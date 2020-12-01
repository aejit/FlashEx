package com.flashex.tripplanningmicroservice.workerservice.schedules;

import com.flashex.tripplanningmicroservice.lib.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class EndOfDay {


    @Autowired
    AnalyticsService analyticsService;


    @Scheduled(cron="0 0 11 * * *")
    public void scheduledSummaryCreation(){
        LocalDateTime today = LocalDateTime.now();
        analyticsService.createSummary(today.getYear(), today.getMonthValue(),today.getDayOfMonth());
    }
}
