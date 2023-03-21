package com.whz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.whz.entity.Menu;
import com.whz.entity.Role;
import com.whz.entity.RoleMenu;
import com.whz.mapper.RoleMapper;
import com.whz.service.IMenuService;
import com.whz.service.IRoleMenuService;
import com.whz.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    IRoleMenuService roleMenuService;

    @Resource
    IMenuService menuService;

    @Override
    public Long selectByFlag(String flag) {
        return this.baseMapper.selectByFlag(flag);
    }

    @Transactional
    @Override
    public void setRoleMenu(Long roleId, List<Long> menuIds) {

        // 先删除当前角色id所有的绑定关系
        roleMenuService.deleteByRoleId(roleId);

        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        List<Long> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Long menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) { // 二级菜单 并且传过来的menuId数组里面没有它的父级id
                // 那么我们就得补上这个父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuService.save(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuService.save(roleMenu);
        }
    }

    @Override
    public List<String> getRoleMenu(Long roleId) {
        return roleMenuService.selectByRoleId(roleId);
    }
}
