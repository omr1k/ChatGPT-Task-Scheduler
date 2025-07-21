package com.example.demo.controller;

import com.example.demo.model.Schedule;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private SchedulerService schedulerService;
    
    // POST /schedules - Create a new scheduled prompt
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        if (schedule.getPromptText() == null || schedule.getPromptText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        if (schedule.getCronExpression() == null || schedule.getCronExpression().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(savedSchedule);
    }
    
    // GET /schedules - List all scheduled prompts
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return ResponseEntity.ok(schedules);
    }
    
    // GET /schedules/{id} - Get specific schedule details
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // PUT /schedules/{id} - Edit/Update a schedule
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        
        if (optionalSchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Schedule schedule = optionalSchedule.get();
        
        if (scheduleDetails.getPromptText() != null) {
            schedule.setPromptText(scheduleDetails.getPromptText());
        }
        
        if (scheduleDetails.getCronExpression() != null) {
            schedule.setCronExpression(scheduleDetails.getCronExpression());
        }
        
        if (scheduleDetails.getActive() != null) {
            schedule.setActive(scheduleDetails.getActive());
        }
        
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    // DELETE /schedules/{id} - Delete a schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (!scheduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        scheduleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // POST /schedules/{id}/execute - Manually execute a schedule (for testing)
    @PostMapping("/{id}/execute")
    public ResponseEntity<String> executeSchedule(@PathVariable Long id) {
        if (!scheduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        schedulerService.executeScheduleManually(id);
        return ResponseEntity.ok("Schedule execution triggered");
    }
} 