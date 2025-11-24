package com.ChatApp.messaging_stomp_websocket.Repo;

import com.ChatApp.messaging_stomp_websocket.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageSaverRepo extends JpaRepository<ChatMessage, Integer> {


}
