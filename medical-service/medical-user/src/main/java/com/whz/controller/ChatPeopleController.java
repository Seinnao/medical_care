package com.whz.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whz.entity.ChatPeople;
import com.whz.mapper.ChatPeopleMapper;
import com.whz.service.IChatPeopleService;
import com.whz.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/chat-people")
public class ChatPeopleController {

    @Resource
    IChatPeopleService chatPeopleService;

    @Resource
    ChatPeopleMapper chatPeopleMapper;

    @PostMapping
    public R save(@RequestBody ChatPeople chatPeople) {

        ChatPeople one = chatPeopleService.getOne(Wrappers
                .<ChatPeople>lambdaQuery()
                .eq(ChatPeople::getNickname, chatPeople.getNickname())
                .eq(ChatPeople::getOtherParty, chatPeople.getOtherParty()));

        ChatPeople two = chatPeopleService.getOne(Wrappers
                .<ChatPeople>lambdaQuery()
                .eq(ChatPeople::getNickname, chatPeople.getOtherParty())
                .eq(ChatPeople::getOtherParty,chatPeople.getNickname()));

        ChatPeople people = ChatPeople.builder()
                .nickname(chatPeople.getOtherParty())
                .otherParty(chatPeople.getNickname())
                .time(chatPeople.getTime()).build();

        chatPeople.setAvatarUrl(chatPeople.getToUrl());
        people.setAvatarUrl(chatPeople.getMyUrl());

        if(null == one){
            chatPeople.setUnreadMsg(0);
            chatPeopleService.save(chatPeople);
            chatPeopleService.save(people);
        }else{
            chatPeople.setId(one.getId());
            chatPeopleService.updateById(chatPeople);
            people.setId(two.getId());
            chatPeopleService.updateById(people);
        }

        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(chatPeopleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(chatPeopleService.removeByIds(ids));
    }

    @GetMapping("/{name}")
    public R findAll(@PathVariable(value = "name") String name) {

        List<ChatPeople> list = chatPeopleMapper.findAllByNickname(name);

        return R.ok().put(list);
    }


}
