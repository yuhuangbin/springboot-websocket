package com.yuhb.websocket.controller;

import com.yuhb.websocket.entity.RequstMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Description:
 * author: yu.hb
 * Date: 2019-08-16
 */
@RestController
public class WebSocketController {

    private static final String CHAT_COUNT_KEY = "chat:count";

    // 使用 SimpMessagingTemplate 向客户端发送消息
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加入聊天
     * @param name
     * @return
     */
    @MessageMapping("/addChat")
    public void addChat(@Payload String name) throws InterruptedException {
        Thread.sleep(5);
        // 向客户端推送 欢迎语
        simpMessagingTemplate.convertAndSend("/topic/chat/addnotify",name);

        incrCount();

        String count = stringRedisTemplate.opsForValue().get(CHAT_COUNT_KEY);
        simpMessagingTemplate.convertAndSend("/topic/chat/count",count);
    }

    /**
     * 退出聊天
     * @return
     */
    @MessageMapping("/exitChat")
    @SendTo("/topic/chat/count")
    public String exitChat() {
        stringRedisTemplate.opsForValue().decrement(CHAT_COUNT_KEY);
        return stringRedisTemplate.opsForValue().get(CHAT_COUNT_KEY);
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

    private void incrCount() {
        String scriptCommand = "if redis.call('SETNX',KEYS[1], 1) == 1 then return 1 " +
                               "elseif redis.call('INCR',KEYS[1]) then return 1 end " +
                                "return 0";
        DefaultRedisScript script = new DefaultRedisScript();
        script.setResultType(Long.class);
        script.setScriptText(scriptCommand);

        stringRedisTemplate.execute(script, Arrays.asList(CHAT_COUNT_KEY),"");

    }



}
