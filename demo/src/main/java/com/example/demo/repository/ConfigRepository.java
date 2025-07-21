package com.example.demo.repository;

import com.example.demo.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    // Since we'll only have one config record, we can add a method to find it
    Config findFirstByOrderByIdAsc();
} 