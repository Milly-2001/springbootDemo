package com.example.springbootdemo.config;

import com.example.springbootdemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 允许所有人访问登录页面和静态资源
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // 自定义登录页面
                        .loginPage("/login.html")
                        // 登录表单提交的URL
                        .loginProcessingUrl("/login")
                        // 登录成功后的跳转页面
                        .defaultSuccessUrl("/home", true)
                        // 登录失败后的跳转页面
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                // 启用“记住我”功能的。允许用户在关闭浏览器后，仍然保持登录状态，直到他们主动注销或超出设定的过期时间。
                .rememberMe(Customizer.withDefaults());
               // 关闭 csrf CSRF（跨站请求伪造）是一种网络攻击，攻击者通过欺骗已登录用户，诱使他们在不知情的情况下向受信任的网站发送请求。
            http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
