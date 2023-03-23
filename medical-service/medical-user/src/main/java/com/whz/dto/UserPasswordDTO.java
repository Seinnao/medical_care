package com.whz.dto;

import lombok.Data;

/**
 * @author 文辉正
 * @since 2023/3/23 23:55
 */
@Data
public class UserPasswordDTO {

    private String username;
    private String password;
    private String newPassword;

}
