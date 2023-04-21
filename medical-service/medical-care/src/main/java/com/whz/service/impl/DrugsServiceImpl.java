package com.whz.service.impl;

import com.whz.entity.Drugs;
import com.whz.mapper.DrugsMapper;
import com.whz.service.IDrugsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
@Service
public class DrugsServiceImpl extends ServiceImpl<DrugsMapper, Drugs> implements IDrugsService {

    @Override
    public List<Drugs> selectListByName(String name) {
        return this.baseMapper.selectListByName(name);
    }

    @Override
    public Drugs selectListById(Long id) {
        return this.baseMapper.selectListById(id);
    }
}
