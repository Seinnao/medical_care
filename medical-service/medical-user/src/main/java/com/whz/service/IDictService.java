package com.whz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whz.entity.Dict;
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
public interface IDictService extends IService<Dict> {

    List<Dict> selectList(QueryWrapper<Dict> queryWrapper);
}
