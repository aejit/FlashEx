package com.flashex.tripplanningmicroservice.webservice.controller;

import com.flashex.tripplanningmicroservice.lib.model.OptimizationProperties;
import com.flashex.tripplanningmicroservice.lib.services.OptimizationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ConfigPropertiesController {

    @Autowired
    OptimizationPropertiesService optPropService;

    @GetMapping("/optprops")
    public List<OptimizationProperties> getAllProperties() {
        return optPropService.getAllProperties();
    }

    @GetMapping("/optprops/{id}")
    public OptimizationProperties getSpecificProperties(@PathVariable("id") String id){
        return optPropService.getSpecificProperties(id);
    }

    @PostMapping("/optprops")
    public void saveProperties(@RequestBody OptimizationProperties properties) {
        this.optPropService.saveOptimizationProperties(properties);
    }

    @PutMapping("/optprops")
    public OptimizationProperties modifyProperties(@RequestBody OptimizationProperties properties) {
        return this.optPropService.updateProperties(properties);
    }
}
