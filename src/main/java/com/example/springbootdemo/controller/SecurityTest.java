package com.example.springbootdemo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.example.springbootdemo.model.User;
import com.example.springbootdemo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

@Controller
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
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registerUser")
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
        return "redirect:/login";
    }

    @RequestMapping("/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
            ICaptcha captcha=CaptchaUtil.createCircleCaptcha(100,33,4,10,1);
            request.getSession().setAttribute("captcha",captcha.getCode());
            captcha.write(response.getOutputStream());
    }
}
