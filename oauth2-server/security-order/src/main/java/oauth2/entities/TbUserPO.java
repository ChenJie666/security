package oauth2.entities;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 16:19
 */
@Data
@Accessors(chain = true)
public class TbUserPO {

//    /**
//     * 用户名
//     */
//    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户没有过期
     */
    boolean accountNonExpired = true;
    /**
     * 账户没被锁定 （是否冻结）
     */
    boolean accountNonLocked = true;
    /**
     * 密码没有过期
     */
    boolean credentialsNonExpired = true;
    /**
     * 账户是否可用（是否被删除）
     */
    boolean enabled = true;

    /*--------------------------------------------*/

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;

}
