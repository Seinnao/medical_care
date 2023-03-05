package com.whz.service.impl;

import com.whz.entity.Role;
import com.whz.mapper.RoleMapper;
import com.whz.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public Integer selectByFlag(String flag) {
        return this.baseMapper.selectByFlag(flag);
    }
}
