package com.whz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whz.entity.Forum;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-21
 */
public interface IForumService extends IService<Forum> {

    IPage<Forum> pageList(Page<Forum> page,Forum forum);

    Forum queryById(Long id);

}
