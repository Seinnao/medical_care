package com.whz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文辉正
 * @since 2023/3/3 21:34
 */
@RestController
public class TestController {

    @GetMapping("test")
    void Test(){
        System.out.println("user-service");
    }

}
