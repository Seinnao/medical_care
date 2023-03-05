package com.whz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whz.dto.UserDTO;
import com.whz.entity.Menu;
import com.whz.entity.User;
import com.whz.exception.ServiceException;
import com.whz.mapper.UserMapper;
import com.whz.service.IMenuService;
import com.whz.service.IRoleMenuService;
import com.whz.service.IRoleService;
import com.whz.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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


    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one = this.getOne(queryWrapper);
        if(one != null){
            BeanUtil.copyProperties(one, userDTO, true);//将one上相同属性拷贝到userDTO
            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else{
            throw new ServiceException("用户名或密码错误");
        }
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag 权限
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleService.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
