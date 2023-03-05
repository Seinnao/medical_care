package com.whz.controller;


import com.whz.dto.UserDTO;
import com.whz.service.IUserService;
import com.whz.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService userService;

    @PostMapping("/login")
    public R login(@RequestBody UserDTO userDTO) {
        UserDTO dto = userService.login(userDTO);
        System.out.println(userDTO);
        return R.ok().put(dto);
    }


}
