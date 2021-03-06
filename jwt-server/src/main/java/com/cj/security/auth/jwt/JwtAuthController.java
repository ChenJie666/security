package com.cj.security.auth.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@Slf4j
public class JwtAuthController {

    @Resource
    JwtAuthService jwtAuthService;

    /**
     * 根据账号密码获取token
     * @param map
     * @return
     */
    @RequestMapping(value = "/authentication")
    public CommonResult login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("账号或密码不能为空");
        }

        String token = jwtAuthService.login(username, password);

        log.info("token:"+token);
        return CommonResult.success().setData(token);
    }

    /**
     * 刷新请求头中的token令牌
     * @param token
     * @return
     */
    @RequestMapping(value = "/refreshToken")
    public CommonResult refresh(@RequestHeader("${jwt.header}") String token) {
        String newToken = jwtAuthService.refreshToken(token);
        return CommonResult.success().setData(newToken);
    }

}
