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

    //UsernamePasswordAuthenticationFilter最终会将用户名密码通过AuthenticationManager的authenticate方法进行验证
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
            //通过该方法对账号密码进行认证，认证不通过则抛出异常。此处会调用UserDetailsService获取用户信息进行校验。
            Authentication authenticate = authenticationManager.authenticate(upToken);
            //认证通过则将认证结果放入上下文
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("账号密码认证不通过");
        }

        //验证通过后，根据username查询数据库中的该用户的详细信息，生成token返回
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     * 刷新请求头中的token令牌
     *
     * @param oldToken
     * @return
     */
    public String refreshToken(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        }

        return null;
    }


}
