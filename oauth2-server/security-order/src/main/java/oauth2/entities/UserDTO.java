package oauth2.entities;

import lombok.Data;

@Data
public class UserDTO {

    /**
     *
     */
    private String id;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String phone;

    boolean accountNonExpired = true; // 账号是否过期
    boolean accountNonLocked = true; //用户是否被锁定
    boolean credentialsNonExpired = true; //凭证是否过期
    boolean enabled = true; //账号是否可用

}
