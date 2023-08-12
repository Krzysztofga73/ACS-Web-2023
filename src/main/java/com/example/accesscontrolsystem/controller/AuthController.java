package com.example.accesscontrolsystem.controller;

import jakarta.persistence.Column;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLogin() {
        return "login/login";
    }

    @GetMapping("/loginError")
    public String showError() {
        return "login/login-error";
    }
}
