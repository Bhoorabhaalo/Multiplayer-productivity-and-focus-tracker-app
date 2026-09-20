package com.focusforge.service;

import com.focusforge.dto.request.CreateTaskRequest;
import com.focusforge.dto.request.UpdateTaskRequest;
import com.focusforge.dto.response.TaskResponse;
import com.focusforge.dto.response.TaskSummaryResponse;
import com.focusforge.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<TaskResponse> getTasks(Long userId, Task.TaskStatus status, LocalDate date);
    TaskResponse createTask(Long userId, CreateTaskRequest request);
    TaskResponse updateTask(Long userId, Long taskId, UpdateTaskRequest request);
    void deleteTask(Long userId, Long taskId);
    TaskResponse completeTask(Long userId, Long taskId);
    TaskSummaryResponse getTodaySummary(Long userId);
}
