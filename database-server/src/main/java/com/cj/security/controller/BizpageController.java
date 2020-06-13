package com.cj.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CJ
 * @Data: 2020/6/8 16:47
 */
@RestController
public class BizpageController {

    //登录
    @PostMapping("/login1")
    public String index(String username, String password) {
        return "index";
    }

//     日志管理
    @GetMapping("/users/view/syslog")
    @ResponseBody
    public String showOrder() {
        return "syslog";
    }

    // 用户管理
    @GetMapping("/users/view/sysuser")
    public String addOrder() {
        return "sysuser";
    }

    // 具体业务一
    @GetMapping("/contents/view/biz1")
    public String updateOrder() {
        return "biz1";
    }

    // 具体业务二
    @GetMapping("/contents/view/biz2")
    public String deleteOrder() {
        return "biz2";
    }

}
