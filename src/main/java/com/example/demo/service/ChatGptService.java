package com.example.demo.service;

import com.example.demo.model.Config;
import com.example.demo.model.Result;
import com.example.demo.model.Schedule;
import com.example.demo.repository.ConfigRepository;
import com.example.demo.repository.ResultRepository;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ChatGptService {
    
    @Autowired
    private ConfigRepository configRepository;
    
    @Autowired
    private ResultRepository resultRepository;
    
    @Autowired
    private EmailService emailService;
    
    public Result executeSchedule(Schedule schedule) {
        try {
            // Get the API key from config
            Config config = configRepository.findFirstByOrderByIdAsc();
            if (config == null || config.getOpenaiApiKey() == null) {
                return createErrorResult(schedule, "OpenAI API key not configured");
            }
            
            // Create OpenAI service
            OpenAiService service = new OpenAiService(config.getOpenaiApiKey(), Duration.ofSeconds(60));
            
            // Create chat completion request
            String model = (config.getGptModel() != null && !config.getGptModel().isEmpty()) ? config.getGptModel() : "gpt-3.5-turbo";
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(List.of(new ChatMessage("user", schedule.getPromptText())))
                    .maxTokens(1000)
                    .build();
            
            // Get response from ChatGPT
            String response = service.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();
            
            // Save successful result
            Result result = new Result(schedule, response, "SUCCESS");
            result = resultRepository.save(result);
            
            // Send email with the result
            emailService.sendChatGptResult(result);
            
            return result;
            
        } catch (Exception e) {
            // Save error result
            String errorMessage = "Error: " + e.getMessage();
            return createErrorResult(schedule, errorMessage);
        }
    }
    
    private Result createErrorResult(Schedule schedule, String errorMessage) {
        Result result = new Result(schedule, errorMessage, "FAILED");
        return resultRepository.save(result);
    }
} 