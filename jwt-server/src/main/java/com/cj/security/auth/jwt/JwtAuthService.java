package com.cj.security.auth.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: CJ
 * @Data: 2020/6/11 10:09
 */
@Service
public class JwtAuthService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    /**
     * 登录认证换取JWT令牌
     * @return
     */
    public String login(String username,String password){
        try {
            //根据账号密码构造token
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            //token认证不通过，则抛出异常
            Authentication authenticate = authenticationManager.authenticate(upToken);
            //认证通过则将认证结果放入上下文
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("账号密码认证不通过");
        }

        //根据username获取上下文中的userDetails，生成token返回
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

}
