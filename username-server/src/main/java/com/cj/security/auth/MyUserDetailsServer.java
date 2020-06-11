package com.cj.security.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CJ
 * @Data: 2020/6/11 18:09
 */
@Service
public class MyUserDetailsServer implements UserDetailsService {

    @Resource
    private MyUserDetailsServerMapper myUserDetailsServerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUserDetails myUserDetails = myUserDetailsServerMapper.findByUsername(username);

        List<String> roleCodes = myUserDetailsServerMapper.findRoleByUsername(username);

        List<String> authorities = myUserDetailsServerMapper.findAuthorityByRoleCodes(roleCodes);

        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));

        return myUserDetails;
    }

}
