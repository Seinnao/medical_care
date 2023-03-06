package com.whz.service.impl;

import com.google.code.kaptcha.Producer;
import com.whz.dto.UserDTO;
import com.whz.exception.ServiceException;
import com.whz.service.CaptchaService;
import com.whz.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * @author 文辉正
 * @since 2023/3/4 20:03
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public BufferedImage getCaptcha(String uid) {
        //生成文字验证码
        String code = producer.createText();
        //该验证码在redis里面存5分钟过期
        redisUtil.set(uid,code,300);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(UserDTO userDTO) {
        String uid = userDTO.getUid();
        if(redisUtil.isKey(uid)){
            String o = (String) redisUtil.get(uid);
            //redis移除缓存
            redisUtil.delete(uid);
            return userDTO.getCaptcha().equals(o);
        }else{
            throw new ServiceException("验证码已过期");
        }
    }


}
