package com.whz.service.impl;

import com.whz.entity.RoleMenu;
import com.whz.mapper.RoleMenuMapper;
import com.whz.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<Integer> selectByRoleId(Integer roleId) {
        return this.baseMapper.selectByRoleId(roleId);
    }
}
