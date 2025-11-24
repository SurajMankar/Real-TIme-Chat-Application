package com.ChatApp.messaging_stomp_websocket.controller;

import com.ChatApp.messaging_stomp_websocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/private-message")
    public void sendPrivateMessage(ChatMessage message) {
        System.out
                .println(message.getSender().getId() + "" + message.getReceiver().getId() + "" + message.getContent());

        String roomID = Math.max(message.getSender().getId(), message.getReceiver().getId()) + "_"
                + Math.min(message.getSender().getId(), message.getReceiver().getId());
        System.out.println(roomID);
        messagingTemplate.convertAndSend("/user/" + roomID + "/private", message);

    }
}
