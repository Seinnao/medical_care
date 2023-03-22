package com.whz.storage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 文辉正
 * @since 2023/3/22 17:14
 */
public class SessionMap {

    public static final ConcurrentHashMap<String,ChatData> sessionMap = new ConcurrentHashMap<>();

}
