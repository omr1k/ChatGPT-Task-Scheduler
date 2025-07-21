package com.example.demo.controller;

import com.example.demo.model.Config;
import com.example.demo.model.Result;
import com.example.demo.model.Schedule;
import com.example.demo.repository.ConfigRepository;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    
    @Autowired
    private ConfigRepository configRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/")
    public String dashboard(Model model) {
        // Get config status
        Config config = configRepository.findFirstByOrderByIdAsc();
        model.addAttribute("hasApiKey", config != null && config.getOpenaiApiKey() != null);
        model.addAttribute("config", config);
        
        // DB status
        String dbStatus = "Unknown";
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            dbStatus = "Connected";
        } catch (Exception e) {
            dbStatus = "Not Connected";
        }
        model.addAttribute("dbStatus", dbStatus);
        
        // Get all schedules
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        
        // Get recent results
        List<Result> results = resultRepository.findAllOrderByCreatedAtDesc();
        model.addAttribute("results", results);
        
        return "dashboard";
    }
    
    @GetMapping("/web/config")
    public String configPage(Model model) {
        Config config = configRepository.findFirstByOrderByIdAsc();
        model.addAttribute("config", config);
        return "config";
    }
    
    @GetMapping("/web/schedules")
    public String schedulesPage(Model model) {
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "schedules";
    }
    
    @GetMapping("/web/results")
    public String resultsPage(Model model) {
        List<Result> results = resultRepository.findAllOrderByCreatedAtDesc();
        model.addAttribute("results", results);
        return "results";
    }
} 