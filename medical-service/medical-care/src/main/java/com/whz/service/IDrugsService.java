package com.whz.service;

import com.whz.entity.Drugs;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
public interface IDrugsService extends IService<Drugs> {

    List<Drugs> selectListByName(String name);

    Drugs selectListById(Long id);
}
