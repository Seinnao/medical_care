package com.whz.service;

import com.whz.dto.UserDTO;
import com.whz.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    void doRegister(UserDTO userDTO);
}
