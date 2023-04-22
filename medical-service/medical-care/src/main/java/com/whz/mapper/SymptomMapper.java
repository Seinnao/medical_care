package com.whz.mapper;

import com.whz.entity.Disease;
import com.whz.entity.Symptom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
public interface SymptomMapper extends BaseMapper<Symptom> {

    List<Symptom> selectByDisease(@Param("disease") Disease disease);

    Set<Symptom> selectByName(@Param("name") String name);
}
