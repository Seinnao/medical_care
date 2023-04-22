package com.whz.mapper;

import com.whz.entity.Disease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
public interface DiseaseMapper extends BaseMapper<Disease> {

    Set<Disease> getSetByName(@Param("name") String name);

}
