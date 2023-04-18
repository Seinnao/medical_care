package com.whz.mapper;

import com.whz.entity.DrugsSymptom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DrugsSymptomMapper extends BaseMapper<DrugsSymptom> {

    void insertBatch(@Param("list") List<DrugsSymptom> list);

}
