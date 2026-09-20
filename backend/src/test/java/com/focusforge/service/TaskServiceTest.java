package com.focusforge.service;

import com.focusforge.dto.request.CreateTaskRequest;
import com.focusforge.dto.response.TaskResponse;
import com.focusforge.entity.Task;
import com.focusforge.entity.User;
import com.focusforge.repository.PodRepository;
import com.focusforge.repository.TaskRepository;
import com.focusforge.repository.UserRepository;
import com.focusforge.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PodRepository podRepository;
    @Mock
    private AchievementService achievementService;

    private TaskServiceImpl taskService;

    private User testUser;
    private Task testTask;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, userRepository, podRepository, achievementService);
        testUser = User.builder().id(1L).email("alex@focusforge.app").username("alex").displayName("Alex J.")
                .totalXp(100).build();
        testTask = Task.builder().id(10L).user(testUser).title("LeetCode DP").status(Task.TaskStatus.ACTIVE).build();
    }

    @Test
    void createTask_Success() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("LeetCode DP");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any())).thenReturn(testTask);

        TaskResponse response = taskService.createTask(1L, request);

        assertNotNull(response);
        assertEquals("LeetCode DP", response.getTitle());
        assertEquals(Task.TaskStatus.ACTIVE, response.getStatus());
    }

    @Test
    void completeTask_Success_AwardsXp() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = taskService.completeTask(1L, 10L);

        assertNotNull(response);
        assertEquals(Task.TaskStatus.COMPLETED, response.getStatus());
        assertEquals(25, response.getXpAwarded());
        verify(userRepository).save(testUser);
        assertEquals(125, testUser.getTotalXp());
        verify(achievementService).evaluateAchievements(1L);
    }
}
