package com.example.demo.controller;

import com.example.demo.model.Config;
import com.example.demo.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {
    
    @Autowired
    private ConfigRepository configRepository;
    
    // GET /config - View current OpenAI API key (masked)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getConfig() {
        Config config = configRepository.findFirstByOrderByIdAsc();
        
        Map<String, Object> response = new HashMap<>();
        if (config != null) {
            response.put("hasApiKey", true);
            response.put("updatedAt", config.getUpdatedAt());
            // Return the real API key and dbPassword (no masking)
            response.put("apiKey", config.getOpenaiApiKey());
            response.put("userEmail", config.getUserEmail());
            response.put("mailtrapToken", config.getMailtrapToken());
            response.put("dbHost", config.getDbHost());
            response.put("dbPort", config.getDbPort());
            response.put("dbName", config.getDbName());
            response.put("dbUsername", config.getDbUsername());
            response.put("dbPassword", config.getDbPassword());
            response.put("dbParams", config.getDbParams());
            response.put("timeZone", config.getTimeZone());
            response.put("gptModel", config.getGptModel());
        } else {
            response.put("hasApiKey", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    // PUT /config - Update OpenAI API key, email, and Mailtrap token
    @PutMapping
    public ResponseEntity<Config> updateConfig(@RequestBody Map<String, String> request) {
        String apiKey = request.get("openaiApiKey");
        String userEmail = request.get("userEmail");
        String mailtrapToken = request.get("mailtrapToken");
        String dbHost = request.get("dbHost");
        String dbPort = request.get("dbPort");
        String dbName = request.get("dbName");
        String dbUsername = request.get("dbUsername");
        String dbPassword = request.get("dbPassword");
        String dbParams = request.get("dbParams");
        String timeZone = request.get("timeZone");
        String gptModel = request.get("gptModel");
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Config config = configRepository.findFirstByOrderByIdAsc();
        
        if (config == null) {
            config = new Config(apiKey, userEmail, mailtrapToken, dbHost, dbPort, dbName, dbUsername, dbPassword, dbParams);
        } else {
            config.setOpenaiApiKey(apiKey);
            if (userEmail != null && !userEmail.trim().isEmpty()) {
                config.setUserEmail(userEmail);
            }
            if (mailtrapToken != null && !mailtrapToken.trim().isEmpty()) {
                config.setMailtrapToken(mailtrapToken);
            }
            if (dbHost != null) config.setDbHost(dbHost);
            if (dbPort != null) config.setDbPort(dbPort);
            if (dbName != null) config.setDbName(dbName);
            if (dbUsername != null) config.setDbUsername(dbUsername);
            if (dbPassword != null) config.setDbPassword(dbPassword);
            if (dbParams != null) config.setDbParams(dbParams);
            if (timeZone != null) config.setTimeZone(timeZone);
            if (gptModel != null) config.setGptModel(gptModel);
        }
        
        Config savedConfig = configRepository.save(config);
        return ResponseEntity.ok(savedConfig);
    }
} 