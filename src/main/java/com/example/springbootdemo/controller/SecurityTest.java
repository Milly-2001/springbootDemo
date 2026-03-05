package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SecurityTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home() {
        return "home"; // 登录成功后的主页
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setCreated(String.valueOf(new Date()));
        user.setValid(1);
        // 分配默认角色
        // 需要先创建角色并设置
        // user.setRoles(roles);
        userRepository.save(user);
        return "redirect:/login?registered=true";
    }
}
