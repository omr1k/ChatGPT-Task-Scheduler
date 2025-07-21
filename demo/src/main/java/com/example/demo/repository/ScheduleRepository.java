package com.example.demo.repository;

import com.example.demo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByActiveOrderByCreatedAtDesc(Boolean active);
    
    @Query("SELECT s FROM Schedule s WHERE s.active = true")
    List<Schedule> findAllActiveSchedules();
} 