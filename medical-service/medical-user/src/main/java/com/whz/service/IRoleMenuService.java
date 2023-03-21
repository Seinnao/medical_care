package com.whz.service;

import com.whz.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<String> selectByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
