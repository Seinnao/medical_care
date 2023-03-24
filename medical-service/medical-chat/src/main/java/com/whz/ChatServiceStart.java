package com.whz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 文辉正
 * @since 2023/3/22 15:16
 */
@SpringBootApplication
@EnableFeignClients //开启feign的客户端功能
public class ChatServiceStart {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceStart.class,args);
    }
}
