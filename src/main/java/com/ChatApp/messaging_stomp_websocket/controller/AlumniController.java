package com.ChatApp.messaging_stomp_websocket.controller;

import com.ChatApp.messaging_stomp_websocket.Repo.AlumniRepo;
import com.ChatApp.messaging_stomp_websocket.model.Alumni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AlumniController {
    @Autowired
    AlumniRepo alumniRepo;

    @GetMapping("/getAllAlumni")
    public ResponseEntity<List<Alumni>> getAllAlumni() {
        List<Alumni> alumniList = alumniRepo.findAll();
        return new ResponseEntity<>(alumniList, HttpStatus.OK);
    }

    @GetMapping("/login/{name}")
    public ResponseEntity<Alumni> login(@PathVariable String name) {
        Alumni user = alumniRepo.findByNameIgnoreCase(name);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAllExcept/{id}")
    public List<Alumni> getAllExcept(@PathVariable int id) {
        return alumniRepo.findAll().stream()
                .filter(a -> a.getId() != id)
                .toList();
    }

}
