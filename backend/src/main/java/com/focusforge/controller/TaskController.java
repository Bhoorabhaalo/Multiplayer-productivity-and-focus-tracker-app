package com.focusforge.controller;

import com.focusforge.dto.request.CreateTaskRequest;
import com.focusforge.dto.request.UpdateTaskRequest;
import com.focusforge.dto.response.TaskResponse;
import com.focusforge.dto.response.TaskSummaryResponse;
import com.focusforge.entity.Task;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Personal to-do list and focus task management")
public class TaskController {
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get tasks with optional status and date filters")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) Task.TaskStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(taskService.getTasks(principal.getId(), status, date));
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CreateTaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(principal.getId(), request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskResponse> updateTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(principal.getId(), id, request));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Mark a task as complete and award XP")
    public ResponseEntity<TaskResponse> completeTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(principal.getId(), id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id) {
        taskService.deleteTask(principal.getId(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/today")
    @Operation(summary = "Get today's active count, completed count, and XP earned")
    public ResponseEntity<TaskSummaryResponse> getTodaySummary(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(taskService.getTodaySummary(principal.getId()));
    }
}
