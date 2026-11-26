package se.yrgo.UserService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;

import se.yrgo.UserService.data.UserRepository;
import se.yrgo.UserService.domain.User;
import se.yrgo.UserService.dto.RegisterRequest;
import se.yrgo.UserService.dto.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository data;
    private final PasswordEncoder encoder;

    public UserController(UserRepository data, PasswordEncoder encoder) {
        this.data = data;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest req) {
        if (data.existsByUsername(req.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = new User();
        user.setName(req.name());
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserDto.createUserDto(data.save(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@RequestAttribute("userId") Long userId) {
        return data.findById(userId)
                .map(u -> ResponseEntity.ok(UserDto.createUserDto(u)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}