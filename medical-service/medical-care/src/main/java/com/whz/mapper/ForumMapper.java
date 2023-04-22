package com.whz.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Forum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-21
 */
public interface ForumMapper extends BaseMapper<Forum> {

    IPage<Forum> pageList(@Param("page") Page<Forum> page, @Param("forum") Forum forum);

    Forum queryById(@Param("id") Long id);
}
