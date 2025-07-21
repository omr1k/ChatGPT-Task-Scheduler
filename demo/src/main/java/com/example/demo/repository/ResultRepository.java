package com.example.demo.repository;

import com.example.demo.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByScheduleIdOrderByCreatedAtDesc(Long scheduleId);
    
    @Query("SELECT r FROM Result r ORDER BY r.createdAt DESC")
    List<Result> findAllOrderByCreatedAtDesc();
} 