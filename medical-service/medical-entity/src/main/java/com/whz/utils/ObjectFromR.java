package com.whz.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 文辉正
 * @since 2023/3/24 19:04
 */
@Slf4j
public class ObjectFromR{

    public static Object getObject(R r,Class<?> obj) throws Exception{
        return getObject(obj,r,"data");
    }

    public static Object getObject(Class<?> obj,R r,String key) throws Exception{
        String s = JsonUtils.toJson(r.get(key));
        return JsonUtils.fromJson(s, obj);
    }

}
