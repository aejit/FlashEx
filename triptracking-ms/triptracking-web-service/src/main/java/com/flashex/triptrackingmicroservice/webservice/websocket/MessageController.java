package com.flashex.triptrackingmicroservice.webservice.websocket;

import com.flashex.triptrackingmicroservice.lib.model.TripLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.messagingTemplate = template;
    }

    @MessageMapping("/starttrip")
    public void publishMessage(String tripLogMessage) throws Exception {
        this.messagingTemplate.convertAndSend("/start/startdate", tripLogMessage);
    }
    @MessageMapping("/endtrip")
    public void publishMessag(String tripLogMessage) throws Exception {
        this.messagingTemplate.convertAndSend("/end/enddate", tripLogMessage);
    }
}