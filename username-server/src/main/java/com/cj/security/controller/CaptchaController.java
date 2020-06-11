package com.cj.security.controller;

import com.cj.security.auth.imagecode.CaptchaImageVO;
import com.cj.security.utils.MyConstants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: CJ
 * @Data: 2020/6/10 9:30
 */

@RestController
public class CaptchaController {

    @Resource
    private DefaultKaptcha captchaProducer;

    @GetMapping("/kaptcha")
    public void kaptcha(HttpSession session, HttpServletResponse response) {
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");//不能使用缓存
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");

        //生成验证码
        String text = captchaProducer.createText();
        //将验证码和过期时间封装为对象保存到session中
        session.setAttribute(MyConstants.CAPTCHA_SESSION_KEY,new CaptchaImageVO(text,60));
        //写出到输出流中
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            BufferedImage image = captchaProducer.createImage(text);
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
