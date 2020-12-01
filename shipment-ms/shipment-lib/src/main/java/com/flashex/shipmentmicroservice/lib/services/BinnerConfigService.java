package com.flashex.shipmentmicroservice.lib.services;

import com.flashex.shipmentmicroservice.lib.model.BinnerConfig;
import com.flashex.shipmentmicroservice.lib.repository.BinnerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BinnerConfigService {

    @Autowired
    BinnerConfigRepository binnerConfigRepository;

    @Autowired
    BinningService binningService;

    public List<BinnerConfig> getAllConfig(){
        return binnerConfigRepository.findAll();
    }

    public void saveConfig(BinnerConfig binnerConfig){
        binnerConfig.setConfigDate(new Date());
        binnerConfigRepository.save(binnerConfig);
//        binningService.refreshBins();
    }

    public BinnerConfig getCurrentConfig(){
        return getAllConfig().get(getAllConfig().size()-1);
    }
}
