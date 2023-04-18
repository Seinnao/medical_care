package com.whz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whz.entity.Drugs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
public interface DrugsMapper extends BaseMapper<Drugs> {

    List<Drugs> selectListBySymptom(@Param("symptomId") Long symptomId);

}
