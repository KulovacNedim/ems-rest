package dev.ned.application.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketMessageBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

    private AuthChannelInterceptorAdapter authChannelInterceptorAdapter;

    public WebSocketMessageBrokerConfiguration(AuthChannelInterceptorAdapter authChannelInterceptorAdapter) {
        this.authChannelInterceptorAdapter = authChannelInterceptorAdapter;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Handshake endpoint
        registry.addEndpoint("/wsocket")
                .setAllowedOrigins("http://localhost:4200", "http://localhost:4900");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // These are endpoints the client can subscribes to.
        config.enableSimpleBroker("/topic/");
        // Message received with one of those below destinationPrefixes will be automatically router to controllers @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorAdapter);
    }

}