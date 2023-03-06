package com.whz.service;

import com.whz.dto.UserDTO;

import java.awt.image.BufferedImage;

/**
 * @author 文辉正
 * @since 2023/3/4 19:57
 */
public interface CaptchaService {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);


    boolean validate(UserDTO userDTO);

}
