package com.flashex.triptrackingmicroservice.webservice.websocket;

import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private ThreadPoolTaskScheduler taskScheduler;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/start", "/end");
        config.setApplicationDestinationPrefixes("/app");
//        taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(1);
//        taskScheduler.setThreadNamePrefix("ws-heartbeat-thread");
//        taskScheduler.initialize();
//        config.setApplicationDestinationPrefixes("/app");
//        config.enableSimpleBroker("/topic")
//                .setHeartbeatValue(new long[]{5000, 5000})
//                .setTaskScheduler(taskScheduler);
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
//                withSockJS().setClientLibraryUrl( "https://cdn.jsdelivr.net/npm/sockjs-client@1.4.0/dist/sockjs.min.js" );
    }
}
