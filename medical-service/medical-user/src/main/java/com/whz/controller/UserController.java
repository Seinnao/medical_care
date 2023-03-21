package com.whz.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.dto.UserDTO;
import com.whz.entity.User;
import com.whz.service.CaptchaService;
import com.whz.service.IUserService;
import com.whz.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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

    @Resource
    CaptchaService captchaService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, String uid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uid);
        try (//try-with-resources 语法，自动关闭资源
             ServletOutputStream out = response.getOutputStream()
        ) {
            ImageIO.write(image, "jpg", out);
        }
    }


    @PostMapping("/login")
    public R login(@RequestBody UserDTO userDTO) {
        UserDTO dto = userService.login(userDTO);
        System.out.println(userDTO);
        return R.ok().put(dto);
    }

    @GetMapping("/username/{username}")
    public R findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User one = userService.getOne(queryWrapper);
        one.setPassword("");//不暴露密码
        return R.ok().put(one);
    }


    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO){
        userService.doRegister(userDTO);
        return R.ok();
    }


//    @PutMapping("/reset")
//    public R reset(@RequestBody UserPasswordDTO userPasswordDTO) {
//        if (StrUtil.isBlank(userPasswordDTO.getUsername()) || StrUtil.isBlank(userPasswordDTO.getPhone())) {
//            throw new ServiceException("参数异常");
//        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", userPasswordDTO.getUsername());
//        queryWrapper.eq("phone", userPasswordDTO.getPhone());
//        List<User> list = userService.list(queryWrapper);
//        if (CollUtil.isNotEmpty(list)) {
//            User user = list.get(0);
//            user.setPassword("123");
//            userService.updateById(user);
//        }else{
//            throw new ServiceException("用户名或电话号码错误！");
//        }
//        return R.ok();
//    }

//    /**
//     * 修改密码
//     * @param userPasswordDTO
//     */
//    @PostMapping("/password")
//    public R password(@RequestBody UserPasswordDTO userPasswordDTO) {
//        userService.updatePassword(userPasswordDTO);
//        return R.ok();
//    }


    // 新增或者更新
    @PostMapping
    public R save(@RequestBody User user) {
        String username = user.getUsername();
        if (StrUtil.isBlank(username)) {
            return R.error("参数错误");
        }
        if (StrUtil.isBlank(user.getNickname())) {
            user.setNickname(username);
        }
        if (user.getId() != null) {
            user.setPassword(null);
        } else {
            if (user.getPassword() == null) {
                user.setPassword("123");
            }
        }
        return R.ok().put(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(userService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(userService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String username,
                      @RequestParam(defaultValue = "") String email,
                      @RequestParam(defaultValue = "") String address) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return R.ok().put("data",page);
    }

}
