package oauth2.entities;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 16:05
 */
@Data
@Accessors(chain = true)
public class UserInfoDTO {

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
