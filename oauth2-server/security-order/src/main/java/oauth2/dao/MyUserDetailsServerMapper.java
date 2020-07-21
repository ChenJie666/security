package oauth2.dao;

import oauth2.entities.TbUserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: CJ
 * @Data: 2020/6/11 18:09
 */
@Component
public interface MyUserDetailsServerMapper {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Select("SELECT * " +
            "FROM tb_user " +
            "WHERE username=#{username}")
    TbUserPO findByUsername(@Param("username") String username);

    /**
     * 根据用户名查询角色列表
     * @param username
     * @return
     */
    @Select("SELECT r.enname " +
            "FROM tb_role r " +
            "LEFT JOIN tb_user_role ur ON ur.role_id=r.id " +
            "LEFT JOIN tb_user u ON u.id=ur.user_id " +
            "WHERE u.username=#{username}")
    List<String> findRoleByUsername(@Param(value = "username") String username);

    /**
     * 根据用户角色查询权限
     * @param roleCodes
     * @return
     */
    @Select("<script> " +
            "SELECT url " +
            "FROM tb_permission p " +
            "LEFT JOIN tb_role_permission rp ON rp.permission_id=p.id " +
            "LEFT JOIN tb_role r ON r.id=rp.role_id " +
            "WHERE r.enname IN " +
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'> " +
            "#{roleCode} " +
            "</foreach> " +
            "</script>")
    List<String> findAuthorityByRoleCodes(@Param(value = "roleCodes") List<String> roleCodes);

}