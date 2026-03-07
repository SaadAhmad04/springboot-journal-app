package com.example.journalApp.config;

import com.example.journalApp.entity.JournalEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EntityConfig {
    @Bean
    public Map<Long , JournalEntity> getJournalEntity(){
        return new HashMap<>();
    }

}
