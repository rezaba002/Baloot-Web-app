package com.example.Baloot5Backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
