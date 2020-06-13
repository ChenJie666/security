package com.cj.security.auth;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: CJ
 * @Data: 2020/6/12 10:15
 */
@Component
public interface MyRBACServerMapper {

    @Select("SELECT url\n" +
            "FROM tb_permission p\n" +
            "LEFT JOIN tb_role_permission rp ON rp.permission_id=p.id\n" +
            "LEFT JOIN tb_role r ON r.id=rp.role_id\n" +
            "LEFT JOIN tb_user_role ur ON ur.user_id=r.id\n" +
            "LEFT JOIN tb_user u ON u.id=ur.user_id\n" +
            "WHERE username=#{username};")
    List<String> findAuthorityByUsername(@Param("username") String username);

}
