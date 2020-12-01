package com.flashex.shipmentmicroservice.webservice.controller;

import com.flashex.shipmentmicroservice.lib.model.BinnerConfig;
import com.flashex.shipmentmicroservice.lib.services.BinnerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BinnerConfigController {

    @Autowired
    BinnerConfigService binnerConfigService;

    @GetMapping("/binner-config")
    public List<BinnerConfig> getAllConfig(){
        return binnerConfigService.getAllConfig();
    }

    @PostMapping("/binner-config")
    public void saveConfig(@RequestBody BinnerConfig binnerConfig){
        binnerConfigService.saveConfig(binnerConfig);
    }

    @PutMapping("/binner-config")
    public void updateConfig(@RequestBody BinnerConfig binnerConfig){
        binnerConfigService.saveConfig(binnerConfig);
    }
}
