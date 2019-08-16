package com.yuhb.websocket.controller;

import com.yuhb.websocket.entity.RequstMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * author: yu.hb
 * Date: 2019-08-16
 */
@RestController
public class WebSocketController {

    // 使用 SimpMessagingTemplate 向客户端发送消息
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 加入聊天
     * @param name
     * @return
     */
    @MessageMapping("/addChat")
    @SendTo("/topic/chat/addnotify")
    public String addChat(@Payload String name) throws InterruptedException {
        Thread.sleep(10);
        return name;
    }

    /**
     * 发消息
     * @param requstMessage
     * @return
     */
    @MessageMapping("/chat")
    @SendTo("/topic/chat/msgnotify")
    public RequstMessage chat(RequstMessage requstMessage) {
        return requstMessage;
    }

}
