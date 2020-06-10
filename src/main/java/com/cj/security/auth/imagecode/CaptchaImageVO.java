package com.cj.security.auth.imagecode;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: CJ
 * @Data: 2020/6/10 9:34
 */
@Data
public class CaptchaImageVO {

    private String code;
    private LocalDateTime expireTime;

    public CaptchaImageVO(String code,int expireAfterSeconds){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

}
