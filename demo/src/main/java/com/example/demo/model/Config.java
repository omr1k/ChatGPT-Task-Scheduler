package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "openai_api_key", nullable = false)
    private String openaiApiKey;
    
    @Column(name = "user_email")
    private String userEmail;
    
    @Column(name = "mailtrap_token")
    private String mailtrapToken;
    
    @Column(name = "db_host")
    private String dbHost;

    @Column(name = "db_port")
    private String dbPort;

    @Column(name = "db_name")
    private String dbName;

    @Column(name = "db_username")
    private String dbUsername;

    @Column(name = "db_password")
    private String dbPassword;

    @Column(name = "db_params")
    private String dbParams;
    
    @Column(name = "time_zone")
    private String timeZone;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "gpt_model")
    private String gptModel;

    // Constructors
    public Config() {}
    
    public Config(String openaiApiKey) {
        this.openaiApiKey = openaiApiKey;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Config(String openaiApiKey, String userEmail) {
        this.openaiApiKey = openaiApiKey;
        this.userEmail = userEmail;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Config(String openaiApiKey, String userEmail, String mailtrapToken) {
        this.openaiApiKey = openaiApiKey;
        this.userEmail = userEmail;
        this.mailtrapToken = mailtrapToken;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Config(String openaiApiKey, String userEmail, String mailtrapToken, String dbHost, String dbPort, String dbName, String dbUsername, String dbPassword, String dbParams) {
        this.openaiApiKey = openaiApiKey;
        this.userEmail = userEmail;
        this.mailtrapToken = mailtrapToken;
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.dbParams = dbParams;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenaiApiKey() {
        return openaiApiKey;
    }

    public void setOpenaiApiKey(String openaiApiKey) {
        this.openaiApiKey = openaiApiKey;
        this.updatedAt = LocalDateTime.now();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public String getMailtrapToken() {
        return mailtrapToken;
    }

    public void setMailtrapToken(String mailtrapToken) {
        this.mailtrapToken = mailtrapToken;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDbHost() { return dbHost; }
    public void setDbHost(String dbHost) { this.dbHost = dbHost; this.updatedAt = LocalDateTime.now(); }

    public String getDbPort() { return dbPort; }
    public void setDbPort(String dbPort) { this.dbPort = dbPort; this.updatedAt = LocalDateTime.now(); }

    public String getDbName() { return dbName; }
    public void setDbName(String dbName) { this.dbName = dbName; this.updatedAt = LocalDateTime.now(); }

    public String getDbUsername() { return dbUsername; }
    public void setDbUsername(String dbUsername) { this.dbUsername = dbUsername; this.updatedAt = LocalDateTime.now(); }

    public String getDbPassword() { return dbPassword; }
    public void setDbPassword(String dbPassword) { this.dbPassword = dbPassword; this.updatedAt = LocalDateTime.now(); }

    public String getDbParams() { return dbParams; }
    public void setDbParams(String dbParams) { this.dbParams = dbParams; this.updatedAt = LocalDateTime.now(); }

    public String getTimeZone() { return timeZone; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; this.updatedAt = LocalDateTime.now(); }

    public String getGptModel() { return gptModel; }
    public void setGptModel(String gptModel) { this.gptModel = gptModel; this.updatedAt = LocalDateTime.now(); }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 