package com.whz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Forum;
import com.whz.mapper.ForumMapper;
import com.whz.service.IForumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-21
 */
@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements IForumService {

    @Override
    public IPage<Forum> pageList(Page<Forum> page, Forum forum) {
        return this.baseMapper.pageList(page,forum);
    }

    @Override
    public Forum queryById(Long id) {
        return this.baseMapper.queryById(id);
    }

    @Override
    public List<Forum> getMyCollection(String userId) {
        return this.baseMapper.getMyCollection(userId);
    }
}
