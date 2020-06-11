package com.cj.security.auth.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cj.security.utils.CommonResult;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: CJ
 * @Data: 2020/6/11 9:59
 */
@RestController
public class JwtAuthController {

    @Resource
    JwtAuthService jwtAuthService;

    @RequestMapping(value = "/authentication")
    public CommonResult login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("账号或密码不能为空");
        }

        String token = jwtAuthService.login(username, password);

        return CommonResult.success().setData(token);
    }

//    @RequestMapping(value = "")
//    public CommonResult refresh(){
//
//    }

}
