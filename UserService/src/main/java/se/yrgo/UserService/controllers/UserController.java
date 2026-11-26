package se.yrgo.UserService.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import se.yrgo.UserService.data.UserRepository;
import se.yrgo.UserService.domain.User;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository data;

    public UserController(UserRepository data) {
        this.data = data;
    }

    @PostMapping("/register")
    public String newUser(@ModelAttribute User user, HttpSession session) {
        User saved = data.save(user);
        session.setAttribute("userId", saved.getId());
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
}