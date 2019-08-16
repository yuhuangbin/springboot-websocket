package com.yuhb.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Description:
 *  @EnableWebSocketMessageBroker 注解用于开启使用 STOMP 协议来传输基于代理（MessageBroker）的消息，这时候控制器(controller) 支持@MessageMapping,就像是使用 @requestMapping 一样。
 * author: yu.hb
 * Date: 2019-08-16
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个 /endpointChat Stomp端点，并指定使用 SockJS 协议
        registry.addEndpoint("/endpointChat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 广播式配置名为 /topic 消息代理 , 这个消息代理必须和 controller 中的 @SendTo 配置的地址前缀一样或者全匹配
        // 点对点增加一个 /queue 消息代理
        registry.enableSimpleBroker("/queue","/topic");

        // 客户端向服务端发送 需要加下 /app 前缀
        registry.setApplicationDestinationPrefixes("/app");

        // 给指定用户发送一对一的主题前缀是"/user" 默认也是 /user
        registry.setUserDestinationPrefix("/user");
    }
}
