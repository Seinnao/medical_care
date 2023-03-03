package com.whz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 文辉正
 * @since 2023/3/3 21:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserAppStart {

    public static void main(String[] args) {
        SpringApplication.run(UserAppStart.class, args);
    }

}
