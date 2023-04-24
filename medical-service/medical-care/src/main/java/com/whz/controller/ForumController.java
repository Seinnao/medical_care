package com.whz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Forum;
import com.whz.entity.ForumCollection;
import com.whz.mapper.ForumCollectionMapper;
import com.whz.service.IForumService;
import com.whz.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/forum")
public class ForumController {

    @Resource
    IForumService forumService;

    @Resource
    ForumCollectionMapper collectionMapper;

    @PostMapping
    public R save(@RequestBody Forum forum) {
        if(null == forum.getId()){
            forum.setTime(new Date());
            forum.setSee(0);
        }
        forumService.saveOrUpdate(forum);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(forumService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(forumService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(forumService.list());
    }

    @GetMapping("/getPost/{id}")
    public R getPost(@PathVariable Long id){
        Forum forum = forumService.queryById(id);
        forum.setSee(forum.getSee()+1);
        forumService.updateById(forum);
        return R.ok().put(forum);
    }


    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String nickname,
                      @RequestParam(defaultValue = "") String title){
        Forum forum = Forum.builder().title(title).nickname(nickname).build();
        IPage<Forum> page = forumService.pageList(new Page<>(pageNum, pageSize), forum);
        return R.ok().put("data",page);
    }

    @PostMapping("/collection")
    public R collection(@RequestBody ForumCollection collection){
        if(null == collection.getId()){
            collectionMapper.insert(collection);
        }else {
            collectionMapper.updateById(collection);
        }
        return R.ok();
    }

    @GetMapping("/collection/{userId}/{forumId}")
    public R getCollection(@PathVariable String userId,
                           @PathVariable String forumId){
        ForumCollection collection = collectionMapper.selectOne(Wrappers
                .<ForumCollection>lambdaQuery()
                .eq(ForumCollection::getUserId, userId)
                .eq(ForumCollection::getForumId, forumId));
        return R.ok().put(collection);
    }


}
