package com.whz.mapper;

import com.whz.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
public interface RoleMapper extends BaseMapper<Role> {

    Long selectByFlag(@Param("flag") String flag);

}
