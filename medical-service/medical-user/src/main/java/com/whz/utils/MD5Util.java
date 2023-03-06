package com.whz.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Objects;

/**
 * @author 文辉正
 * @since 2023/3/6 18:09
 */
@Component
public class MD5Util {

    //加盐md5盐值
    @Value("${md5.salt}")
    private String DEFAULT_MD_5_SALT;

    /**
     * 获得字符串的md5值
     * @return md5加密后的字符串
     */
    public String getMd5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    /**
     * 校验字符串的md5值
     * @param str 目标字符串
     * @param md5 基准md5
     * @return 校验结果
     */
    public boolean checkMd5(String str, String md5) {
        return Objects.requireNonNull(getMd5(str)).equalsIgnoreCase(md5);
    }


    /**
     * 校验字符串的md5值
     * @param str 目标字符串
     * @param saltMd5 加盐后的MD5值
     * @return 校验结果
     */
    public boolean checkMd5AndSalt(String str, String saltMd5) {
        return Objects.requireNonNull(getMd5AndSalt(str)).equalsIgnoreCase(saltMd5);
    }

    /**
     * 获得加盐md5，算法过程是原字符串md5后连接加盐字符串后再进行md5
     * 使用默认盐值
     *
     * @param str 待加密的字符串
     * @return 加盐md5
     */
    public String getMd5AndSalt(String str) {
        return getMd5(Objects.requireNonNull(getMd5(str)).concat(DEFAULT_MD_5_SALT));
    }
}
