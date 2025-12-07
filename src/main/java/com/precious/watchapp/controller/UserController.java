package com.precious.watchapp.controller;

import com.precious.watchapp.model.User;
import com.precious.watchapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(
        origins = {"http://localhost:8080", "http://localhost:8081"},
        allowCredentials = "true"
)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        Map<String, String> resp = new HashMap<>();
        User saved = service.register(user);

        if (saved == null) {
            resp.put("status", "fail");
            resp.put("message", "Email Already Exists");
        } else {
            resp.put("status", "success");
            resp.put("message", "Registration Successful");
        }
        return resp;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpSession session) {
        Map<String, Object> resp = new HashMap<>();

        User valid = service.login(user.getEmail(), user.getPassword());

        if (valid == null) {
            resp.put("status", "fail");
            resp.put("message", "Invalid Email or Password");
        } else {
            session.setAttribute("USER", valid);  // ⭐ SESSION HERE
            resp.put("status", "success");
            resp.put("message", "Login Successful");
            resp.put("name", valid.getName());
        }
        return resp;
    }

    // ⭐ CHECK USER LOGIN
    @GetMapping("/check")
    public Map<String, Object> checkLogin(HttpSession session) {
        Map<String, Object> resp = new HashMap<>();
        Object u = session.getAttribute("USER");

        resp.put("loggedIn", u != null);
        return resp;
    }

    // ⭐ LOGOUT
    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        return Map.of("message", "Logged out");
    }
}
