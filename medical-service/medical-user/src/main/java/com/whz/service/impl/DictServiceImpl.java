package com.whz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whz.entity.Dict;
import com.whz.mapper.DictMapper;
import com.whz.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public List<Dict> selectList(QueryWrapper<Dict> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

}
