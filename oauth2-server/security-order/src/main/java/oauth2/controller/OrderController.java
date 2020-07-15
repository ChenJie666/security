package oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/15 14:53
 */
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OrderController {

    @GetMapping(value = "/r/contents")
    @PreAuthorize("hasAnyAuthority('p1')") //方法执行前进行判断，拥有p1权限可以访问该url；
    public String contents() {
        return "访问资源contents";
    }

    @GetMapping(value = "/r/users")
    public String users() {
        return "访问资源users";
    }

    @GetMapping(value = "/list")
    public String r1() {
        return "访问资源list";
    }

}
