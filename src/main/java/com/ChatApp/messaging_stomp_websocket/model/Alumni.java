package com.ChatApp.messaging_stomp_websocket.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Alumni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






    @OneToMany(mappedBy = "sender")
    List<ChatMessage> SendMessage=new ArrayList<>();

    @Override
    public String toString() {
        return "Alumni{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "receiver")

    List<ChatMessage> receivedMessage=new ArrayList<>();

    @JsonIgnore
    public List<ChatMessage> getSendMessage() {
        return SendMessage;
    }

    public void setSendMessage(List<ChatMessage> sendMessage) {
        SendMessage = sendMessage;
    }

    @JsonIgnore
    public List<ChatMessage> getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(List<ChatMessage> receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
}
