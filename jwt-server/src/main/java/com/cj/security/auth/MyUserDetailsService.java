package com.cj.security.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CJ
 * @Data: 2020/6/11 16:14
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     * 将账号密码和权限信息封装到UserDetails对象中返回
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过查询数据库获取用户的账号密码
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByUsername(username);

        List<String> roleCodes = myUserDetailsServiceMapper.findRoleByUsername(username);
        List<String> authorities = myUserDetailsServiceMapper.findAuthorityByRoleCodes(roleCodes);

        //将用户角色添加到用户权限中
        authorities.addAll(roleCodes);

        //设置UserDetails中的authorities属性，需要将String类型转换为GrantedAuthority
        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));

        return myUserDetails;
    }

}
