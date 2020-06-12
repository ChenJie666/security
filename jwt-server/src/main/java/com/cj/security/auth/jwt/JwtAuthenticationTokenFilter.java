package com.cj.security.auth.jwt;

import com.cj.security.auth.MyUserDetailsServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description: 分析token认证拦截器
 * @Author: CJ
 * @Data: 2020/6/12 14:50
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private MyUserDetailsServer myUserDetailsServer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isEmpty(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            log.info(username);
            //用户名非空且token没有进行过校验
            if(!Objects.isNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = myUserDetailsServer.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    //通过用户和权限生成Authentication对象
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    log.info("验证通过");
                    //将Authentication对象设置到上下文中，验证完成，后续拦截器放行
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        log.info("交给后续拦截器");
        //交给后续拦截器验证，如果已设置Authentication对象到上下文中，验证完成，后续拦截器放行
        filterChain.doFilter(request,response);
    }

}
