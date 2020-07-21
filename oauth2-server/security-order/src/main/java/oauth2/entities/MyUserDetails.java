package oauth2.entities;

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
    boolean accountNonExpired = true; // 账户没有过期
    boolean accountNonLocked = true; //账户没被锁定 （是否冻结）
    boolean credentialsNonExpired = true; //密码没有过期
    boolean enabled = true; //账户是否可用（是否被删除）
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
