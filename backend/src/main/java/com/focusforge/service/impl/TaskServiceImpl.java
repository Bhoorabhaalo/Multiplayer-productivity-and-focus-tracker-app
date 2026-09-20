package com.focusforge.service.impl;

import com.focusforge.dto.request.CreateTaskRequest;
import com.focusforge.dto.request.UpdateTaskRequest;
import com.focusforge.dto.response.TaskResponse;
import com.focusforge.dto.response.TaskSummaryResponse;
import com.focusforge.entity.Pod;
import com.focusforge.entity.Task;
import com.focusforge.entity.User;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.exception.UnauthorizedActionException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.PodRepository;
import com.focusforge.repository.TaskRepository;
import com.focusforge.repository.UserRepository;
import com.focusforge.service.AchievementService;
import com.focusforge.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, PodRepository podRepository, AchievementService achievementService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.podRepository = podRepository;
        this.achievementService = achievementService;
    }



    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PodRepository podRepository;
    private final AchievementService achievementService;

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Long userId, Task.TaskStatus status, LocalDate date) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (date != null) {
            startDate = date.atStartOfDay();
            endDate = date.atTime(LocalTime.MAX);
        }

        List<Task> tasks;
        if (date != null || status != null) {
            tasks = taskRepository.findTasksWithFilter(userId, status, startDate, endDate);
        } else {
            tasks = taskRepository.findByUserIdOrderByStartAtAsc(userId);
        }

        return tasks.stream()
                .map(EntityMapper::toTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponse createTask(Long userId, CreateTaskRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (request.getStartAt() != null && request.getDeadlineAt() != null
                && request.getDeadlineAt().isBefore(request.getStartAt())) {
            throw new IllegalArgumentException("Deadline must be after start time");
        }

        Pod pod = null;
        if (request.getPodId() != null) {
            pod = podRepository.findById(request.getPodId()).orElse(null);
        }

        Task task = Task.builder()
                .user(user)
                .title(request.getTitle().trim())
                .note(request.getNote() != null ? request.getNote().trim() : null)
                .pod(pod)
                .priority(request.getPriority() != null ? request.getPriority() : Task.Priority.MEDIUM)
                .startAt(request.getStartAt())
                .deadlineAt(request.getDeadlineAt())
                .pomodoroCycles(request.getPomodoroCycles())
                .status(Task.TaskStatus.ACTIVE)
                .xpAwarded(0)
                .build();

        task = taskRepository.save(task);
        return EntityMapper.toTaskResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long userId, Long taskId, UpdateTaskRequest request) {
        Task task = findUserTask(userId, taskId);

        if (request.getStartAt() != null && request.getDeadlineAt() != null
                && request.getDeadlineAt().isBefore(request.getStartAt())) {
            throw new IllegalArgumentException("Deadline must be after start time");
        }

        if (request.getTitle() != null) task.setTitle(request.getTitle().trim());
        if (request.getNote() != null) task.setNote(request.getNote().trim());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getStartAt() != null) task.setStartAt(request.getStartAt());
        if (request.getDeadlineAt() != null) task.setDeadlineAt(request.getDeadlineAt());
        if (request.getPomodoroCycles() != null) task.setPomodoroCycles(request.getPomodoroCycles());

        if (request.getPodId() != null) {
            Pod pod = podRepository.findById(request.getPodId()).orElse(null);
            task.setPod(pod);
        }

        if (request.getStatus() != null && request.getStatus() != task.getStatus()) {
            if (request.getStatus() == Task.TaskStatus.COMPLETED) {
                return completeTask(userId, taskId);
            } else {
                task.setStatus(Task.TaskStatus.ACTIVE);
                task.setCompletedAt(null);
            }
        }

        task = taskRepository.save(task);
        return EntityMapper.toTaskResponse(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long userId, Long taskId) {
        Task task = findUserTask(userId, taskId);
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskResponse completeTask(Long userId, Long taskId) {
        Task task = findUserTask(userId, taskId);

        if (task.getStatus() == Task.TaskStatus.COMPLETED) {
            return EntityMapper.toTaskResponse(task);
        }

        task.setStatus(Task.TaskStatus.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());
        task.setXpAwarded(25); // +25 XP per task
        task = taskRepository.save(task);

        User user = task.getUser();
        user.setTotalXp(user.getTotalXp() + 25);
        user.setLevel(EntityMapper.calculateLevel(user.getTotalXp()));
        userRepository.save(user);

        achievementService.evaluateAchievements(userId);

        return EntityMapper.toTaskResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskSummaryResponse getTodaySummary(Long userId) {
        long activeCount = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.ACTIVE);
        long completedCount = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.COMPLETED);

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        int xpEarnedToday = taskRepository.sumXpEarnedToday(userId, startOfDay, endOfDay);

        return TaskSummaryResponse.builder()
                .activeCount(activeCount)
                .completedCount(completedCount)
                .xpEarnedToday(xpEarnedToday)
                .build();
    }

    private Task findUserTask(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if (!task.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException("You do not have permission to modify this task");
        }
        return task;
    }
}
