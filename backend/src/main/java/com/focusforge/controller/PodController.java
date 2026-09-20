package com.focusforge.controller;

import com.focusforge.dto.response.PodResponse;
import com.focusforge.service.PodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pods")
@Tag(name = "Pods", description = "Course and subject tags catalog")
public class PodController {
    public PodController(PodService podService) {
        this.podService = podService;
    }



    private final PodService podService;

    @GetMapping
    @Operation(summary = "List all available course and subject pods")
    public ResponseEntity<List<PodResponse>> getAllPods() {
        return ResponseEntity.ok(podService.getAllPods());
    }
}
