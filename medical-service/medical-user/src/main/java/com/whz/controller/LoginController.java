package com.whz.controller;

import com.whz.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 文辉正
 * @since 2023/3/4 19:53
 */
@RestController
public class LoginController {

    @Resource
    CaptchaService captchaService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uuid);
        try (//try-with-resources 语法，自动关闭资源
             ServletOutputStream out = response.getOutputStream()
        ) {
            ImageIO.write(image, "jpg", out);
        }
    }

}
