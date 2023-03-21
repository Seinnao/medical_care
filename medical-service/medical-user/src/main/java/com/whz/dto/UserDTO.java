package com.whz.dto;

import com.whz.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 文辉正
 * @since 2023/3/5 17:46
 * 接受前端登录请求的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String tel;
    private String uid;
    private String captcha;
    private String nickname;
    private String avatarUrl;
    private String role;
    private List<Menu> menus;
}
