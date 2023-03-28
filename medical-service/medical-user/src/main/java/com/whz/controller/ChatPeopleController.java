package com.whz.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whz.entity.ChatPeople;
import com.whz.exception.ServiceException;
import com.whz.service.IChatPeopleService;
import com.whz.utils.R;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/chat-people")
public class ChatPeopleController {

    @Resource
    IChatPeopleService chatPeopleService;

    @PostMapping
    public R save(@RequestBody ChatPeople chatPeople) {
        chatPeopleService.saveOrUpdate(chatPeople);
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

        List<ChatPeople> list = chatPeopleService.list(Wrappers
                .<ChatPeople>lambdaQuery()
                .eq(ChatPeople::getNickname, name)
                .or()
                .eq(ChatPeople::getOtherParty, name));

        return R.ok().put(list);
    }


}
