package com.cj.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CJ
 * @Data: 2020/6/8 16:47
 */
@RestController
public class BizpageController {

    @RequestMapping(value = "/users/view")
    public String hello(){
        return "world";
    }

}
