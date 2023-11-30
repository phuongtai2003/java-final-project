package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/headquarter")
public class HeadquarterController {
    @GetMapping("")
    public String index() {
        return "headquarter/index";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return "headquarter/index";
    }
}


class LoginRequest {
    private String email;
    private String password;
}