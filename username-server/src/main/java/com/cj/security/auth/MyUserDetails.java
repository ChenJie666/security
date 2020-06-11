package com.cj.security.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: CJ
 * @Data: 2020/6/11 18:09
 */
@Component
@Data
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    boolean accountNonExpired = true; // 账号是否过期
    boolean accountNonLocked = true; //用户是否被锁定
    boolean credentialsNonExpired = true; //凭证是否过期
    boolean enabled; //账号是否可用
    Collection<? extends GrantedAuthority> authorities; //用户权限集合

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
