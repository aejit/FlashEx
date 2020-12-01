package com.flashex.tripplanningmicroservice.webservice.controller;


import com.flashex.tripplanningmicroservice.lib.model.DaySummary;
import com.flashex.tripplanningmicroservice.lib.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AnalyticsController {

    @Autowired
    AnalyticsService analyticsService;

    @GetMapping("/generated-summary")
    public DaySummary getSummary(@RequestParam String date){
        return analyticsService.getSummaryForDay(date);
    }

    @GetMapping("/generate-summary")
    public List<DaySummary> generateSummary(@RequestParam String date){
        String[] values = date.split("-");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        List<DaySummary> summaries = new ArrayList<>();
        summaries.add(analyticsService.createSummary(year,month,day));
        return summaries;
    }


}
