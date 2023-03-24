package com.whz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whz.dto.UserDTO;
import com.whz.dto.UserPasswordDTO;
import com.whz.entity.Menu;
import com.whz.entity.User;
import com.whz.exception.ServiceException;
import com.whz.mapper.UserMapper;
import com.whz.service.*;
import com.whz.utils.MD5Util;
import com.whz.utils.NickNameUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IMenuService menuService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private MD5Util md5Util;

    @Override
    public UserDTO login(UserDTO userDTO) {
        if(!captchaService.validate(userDTO)){
            throw new ServiceException("验证码错误");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", md5Util.getMd5AndSalt(userDTO.getPassword()));
        User one = this.getOne(queryWrapper);

        if(one != null){
            BeanUtil.copyProperties(one, userDTO, true);//将one上相同属性拷贝到userDTO
            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            userDTO.setPassword("");
            return userDTO;
        }else{
            throw new ServiceException("用户名或密码错误");
        }

    }

    @Override
    public void doRegister(UserDTO userDTO) {
        if(null == userDTO.getUsername()){
            throw new ServiceException("输入数据异常");
        }
        List<User> list = this.list(new QueryWrapper<User>().eq("username", userDTO.getUsername()));
        if(!list.isEmpty()){
            throw new ServiceException("该账号已经被注册");
        }

        //随机一个昵称
        User one;
        String chineseName;
        do{
            chineseName = NickNameUtil.getRandomChineseName();
            one = this.getOne(new QueryWrapper<User>().eq("nickname", chineseName));
        }while (null != one);

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(md5Util.getMd5AndSalt(userDTO.getPassword()))
                .phone(userDTO.getTel())
                .nickname(chineseName)
                .role("ROLE_USER")
                .build();
        this.save(user);//存数据库
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {

        User user = this.getOne(new QueryWrapper<User>()
                .eq("password", md5Util.getMd5AndSalt(userPasswordDTO.getPassword()))
                .eq("", userPasswordDTO.getUsername()));

        if(user != null){
            user.setPassword(md5Util.getMd5AndSalt(userPasswordDTO.getNewPassword()));
            this.updateById(user);
        }else{
            throw new ServiceException("旧密码错误");
        }
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag 权限
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Long roleId = roleService.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<String> menuIds = roleMenuService.selectByRoleId(roleId);
        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId()+"")) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()+""));
        }
        return roleMenus;
    }
}
