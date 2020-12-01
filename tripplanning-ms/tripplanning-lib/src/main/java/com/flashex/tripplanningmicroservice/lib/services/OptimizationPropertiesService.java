package com.flashex.tripplanningmicroservice.lib.services;

import com.flashex.tripplanningmicroservice.lib.model.OptimizationProperties;
import com.flashex.tripplanningmicroservice.lib.repository.OptimizationPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimizationPropertiesService {

    @Autowired
    private OptimizationPropertiesRepository optimizationPropertiesRepository;

    public List<OptimizationProperties> getAllProperties() {
        return this.optimizationPropertiesRepository.findAll();
    }

    public OptimizationProperties getSpecificProperties(String id) {
        return this.optimizationPropertiesRepository.findById(id).orElse(null);
    }

    public void saveOptimizationProperties(OptimizationProperties properties) {
        this.optimizationPropertiesRepository.save(properties);
    }

    public void deleteOptimizationProperties(OptimizationProperties properties) {
        this.optimizationPropertiesRepository.delete(properties);
    }

    public OptimizationProperties updateProperties (OptimizationProperties properties) {
        this.optimizationPropertiesRepository.save(properties);
        return properties;
    }

}
