package com.example.NL_Gym.service;

import com.example.NL_Gym.model.ClassTemplate;
import com.example.NL_Gym.repository.ClassTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassTemplateService {
    private ClassTemplateRepository repository;
    public ClassTemplateService(ClassTemplateRepository repository) {
        this.repository = repository;
    }
    public List<ClassTemplate> getAllTemplates() {
        return repository.findAll();
    }
}
