package com.example.springbootdemo.service;

import com.example.springbootdemo.mapper.UserMapper;
import com.example.springbootdemo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

        private UserMapper userMapper;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userMapper.loadUserByUsername(username);
                    if(user == null){
                        throw new UsernameNotFoundException("用户不存在");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getAuthor()))
                    .build();
        }
}
