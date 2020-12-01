package com.flashex.tripplanningmicroservice.lib.configuration;

import com.flashex.tripplanningmicroservice.lib.ORTools.TimeWindowDelivery;
import com.flashex.tripplanningmicroservice.lib.ORTools.VrpWithCapacityConstraint;
import com.flashex.tripplanningmicroservice.lib.ORTools.VrpWithDroppingVisit;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ORConfig {
    @Bean
    public VrpWithCapacityConstraint vrpWithCapacityConstraint(){
        return  new VrpWithCapacityConstraint();
    }

    @Bean
    public VrpWithDroppingVisit vrpWithDroppingVisit(){
        return new VrpWithDroppingVisit();
    }

    @Bean
    public TimeWindowDelivery timeWindowDelivery(){
        return new TimeWindowDelivery();
    }

    @Bean
    public Data data(){
        return new Data();
    }

}