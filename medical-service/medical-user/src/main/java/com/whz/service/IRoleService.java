package com.whz.service;

import com.whz.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
public interface IRoleService extends IService<Role> {

    Long selectByFlag(String roleFlag);

    void setRoleMenu(Long roleId, List<Long> menuIds);

    List<Long> getRoleMenu(Long roleId);
}
