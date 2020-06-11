package com.cj.security.auth;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MyUserDetailsServer implements UserDetailsService {

    @Resource
    private MyUserDetailsServerMapper myUserDetailsServerMapper;

    /**
     * 将账号密码和权限信息封装到UserDetails对象中返回
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过查询数据库获取用户的账号密码
        MyUserDetails myUserDetails = myUserDetailsServerMapper.findByUsername(username);

        List<String> roleCodes = myUserDetailsServerMapper.findRoleByUsername(username);
        List<String> authorities = myUserDetailsServerMapper.findAuthorityByRoleCodes(roleCodes);

        //将用户角色添加到用户权限中
        authorities.addAll(roleCodes);

        //设置UserDetails中的authorities属性，需要将String类型转换为GrantedAuthority
        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));

        log.info("UserDetail:" + myUserDetails);
        return myUserDetails;
    }

}
