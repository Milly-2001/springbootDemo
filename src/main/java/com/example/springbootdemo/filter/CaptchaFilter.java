package com.example.springbootdemo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String code = request.getParameter("captcha");
        String sessionCode = (String)request.getSession().getAttribute("captcha");
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/toLogin")){
            if (!StringUtils.hasText(code)){
                response.sendRedirect("/");
            } else if (!code.equalsIgnoreCase(sessionCode)) {
                response.sendRedirect("/");
            }else {
                filterChain.doFilter(request, response);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
