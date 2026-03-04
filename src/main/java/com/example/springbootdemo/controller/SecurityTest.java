package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTest {
    @GetMapping("/login")
    public String login() {
        return "login"; // 返回登录页面
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // 登录成功后的主页
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}
