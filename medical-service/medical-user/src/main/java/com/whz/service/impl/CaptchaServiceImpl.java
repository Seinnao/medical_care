package com.whz.service.impl;

import com.google.code.kaptcha.Producer;
import com.whz.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * @author 文辉正
 * @since 2023/3/4 20:03
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        //生成文字验证码
        String code = producer.createText();
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        return false;
    }
}
