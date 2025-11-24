package com.ChatApp.messaging_stomp_websocket.model;


import jakarta.persistence.*;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int messageID;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Alumni sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Alumni receiver;
    private String content;

    public Alumni getSender() {
        return sender;
    }

    public void setSender(Alumni sender) {
        this.sender = sender;
    }

    public Alumni getReceiver() {
        return receiver;
    }

    public void setReceiver(Alumni receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }


}

