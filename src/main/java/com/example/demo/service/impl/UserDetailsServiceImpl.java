package com.example.demo.service.impl;

import com.example.demo.model.domain.Authority;
import com.example.demo.model.domain.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname UserDetailsServiceImpl
 * @Description 自定义一个 UserDetailsService 接口实现类进行用户认证信息封装
 * UserDetailsService 是 Security 提供的进行认证用户信息封装的接口
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 通过(customerService)业务方法(getCustomer,getCustomerAuthority)获取用户及权限信息
        Users users = userService.getUser(s);
        List<Authority> authorityList = userService.getUserAuthority(s);
        // 对用户权限进行封装
        List<SimpleGrantedAuthority> list = authorityList.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
        // 返回封装的UserDetails用户详情类
        if (users != null) {
            UserDetails userDetails = new User(users.getUsername(), users.getPassword(), list);
//            System.out.println(userDetails.getPassword());
            return userDetails;
        } else {
            // 如果查询的用户不存在（用户名不存在），必须抛出此异常
            throw new UsernameNotFoundException("当前用户不存在！");
        }
    }
}

