package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTest {
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/doLogin")
    public String doLogin() {
        return "我登录成功了";
    }
}
