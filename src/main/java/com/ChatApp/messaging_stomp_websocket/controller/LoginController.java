package com.ChatApp.messaging_stomp_websocket.controller;

import com.ChatApp.messaging_stomp_websocket.Repo.AlumniRepo;
import com.ChatApp.messaging_stomp_websocket.model.Alumni;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private AlumniRepo alumniRepo;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html"; // login.html
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String name, Model model, HttpSession session) {

        Alumni user = alumniRepo.findByNameIgnoreCase(name);
        System.out.println(user);

        if (user == null) {
            model.addAttribute("error", "User not found");
            return "redirect:/login";
        }

        session.setAttribute("user", user);
        model.addAttribute("user", user);

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String viewChat(HttpSession session) {
        System.out.println("in chat page," + session.getAttribute("user"));
        return "index.html";
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public Alumni getCurrentUser(HttpSession session) {
        Alumni user = (Alumni) session.getAttribute("user");
        return user;
    }

}
