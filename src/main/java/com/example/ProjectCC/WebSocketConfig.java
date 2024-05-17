package com.example.ProjectCC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler(), "/websocket")
                .setAllowedOrigins("*"); // CORS 허용 설정
    }

    @Bean
    public SocketHandler socketHandler() {
        return new SocketHandler();
    }
}