package com.example.demo.controller;

import com.example.demo.model.Result;
import com.example.demo.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/results")
public class ResultController {
    
    @Autowired
    private ResultRepository resultRepository;
    
    // GET /results - View all results
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultRepository.findAllOrderByCreatedAtDesc();
        return ResponseEntity.ok(results);
    }
    
    // GET /results/{id} - View specific result
    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable Long id) {
        Optional<Result> result = resultRepository.findById(id);
        return result.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // GET /results/schedule/{scheduleId} - View results for a specific schedule
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Result>> getResultsByScheduleId(@PathVariable Long scheduleId) {
        List<Result> results = resultRepository.findByScheduleIdOrderByCreatedAtDesc(scheduleId);
        return ResponseEntity.ok(results);
    }
} 