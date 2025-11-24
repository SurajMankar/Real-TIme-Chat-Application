package com.ChatApp.messaging_stomp_websocket.Repo;

import com.ChatApp.messaging_stomp_websocket.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepo extends JpaRepository<Alumni,Integer> {
    Alumni findByNameIgnoreCase(String name);
}
