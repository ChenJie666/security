package com.cj.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: CJ
 * @Data: 2020/6/12 9:51
 */
@Component(value = "rbacService")
public class MyRBACServer {

    @Resource
    private MyRBACServerMapper myRBACServerMapper;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            List<String> authorities = myRBACServerMapper.findAuthorityByUsername(username);

            return authorities.stream().anyMatch(uri -> new AntPathMatcher().match(uri,request.getRequestURI()));
        }

        return false;
    }

}
