package com.example.demo.service;

import com.example.demo.model.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.cronutils.model.definition.CronDefinitionBuilder;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.List;
import com.example.demo.repository.ConfigRepository;
import com.example.demo.model.Config;

@Service
public class SchedulerService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ChatGptService chatGptService;

    @Autowired
    private ConfigRepository configRepository;
    
    // Run every minute to check for schedules that need execution
    @Scheduled(fixedRate = 60000) // 60 seconds
    public void executeScheduledPrompts() {
        List<Schedule> activeSchedules = scheduleRepository.findAllActiveSchedules();
        
        for (Schedule schedule : activeSchedules) {
            if (shouldExecuteSchedule(schedule)) {
                System.out.println("Executing schedule: " + schedule.getPromptText());
                chatGptService.executeSchedule(schedule);
            }
        }
    }
    
    // Simple scheduling logic - for now, execute every schedule every minute
    // In a production app, you'd use a proper cron parser
    private boolean shouldExecuteSchedule(Schedule schedule) {
        if (!schedule.getActive()) return false;
        String cronExpr = schedule.getCronExpression();
        if (cronExpr == null || cronExpr.isEmpty()) return false;
        String timeZoneId = "UTC";
        Config config = configRepository.findFirstByOrderByIdAsc();
        if (config != null && config.getTimeZone() != null && !config.getTimeZone().isEmpty()) {
            timeZoneId = config.getTimeZone();
        }
        try {
            CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
            Cron cron = parser.parse(cronExpr);
            ExecutionTime executionTime = ExecutionTime.forCron(cron);
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(timeZoneId));
            Optional<ZonedDateTime> lastExec = executionTime.lastExecution(now);
            Optional<ZonedDateTime> nextExec = executionTime.nextExecution(lastExec.orElse(now.minusMinutes(1)));
            return nextExec.isPresent() && nextExec.get().withSecond(0).withNano(0).equals(now.withSecond(0).withNano(0));
        } catch (Exception e) {
            System.err.println("Invalid cron expression or time zone for schedule: " + schedule.getPromptText() + " - " + cronExpr + " tz=" + timeZoneId);
            return false;
        }
    }
    
    // Manual execution endpoint (for testing)
    public void executeScheduleManually(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if (schedule != null) {
            chatGptService.executeSchedule(schedule);
        }
    }
} 