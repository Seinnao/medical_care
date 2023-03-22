package com.whz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author 文辉正
 * @since 2023/3/22 23:52
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 将Json字符串反序列化为对象
     * @param json Json字符串
     * @param clazz 对象类型
     * @return 反序列化后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Json反序列化出错：" + e.getMessage(), e);
        }
    }

    /**
     * 将对象序列化为Json字符串
     * @param obj 对象
     * @return 序列化后的Json字符串
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Json序列化出错：" + e.getMessage(), e);
        }
    }
}
