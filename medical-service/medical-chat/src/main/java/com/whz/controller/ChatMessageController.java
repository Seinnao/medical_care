package com.whz.controller;


import com.whz.entity.ChatMessage;
import com.whz.entity.User;
import com.whz.feign.ChatUserFeign;
import com.whz.mapper.ChatMessageMapper;
import com.whz.service.IChatMessageService;
import com.whz.utils.ObjectFromR;
import com.whz.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-24
 */
@Slf4j
@RestController
@RequestMapping("/chat-message")
public class ChatMessageController {

    @Resource
    ChatUserFeign chatUserFeign;

    @Resource
    ChatMessageMapper chatMessageMapper;

    @Resource
    IChatMessageService chatMessageService;

    @GetMapping("/test")
    public R test() throws Exception {
        R r = chatUserFeign.findByUsername("admin");
        User object = (User) ObjectFromR.getObject(r, User.class);
        return R.ok();
    }

    @GetMapping("/history/{nickname}/{toNickname}")
    public R getHistory(@PathVariable("nickname") String nickname,
                        @PathVariable("toNickname") String toNickname) {

        List<ChatMessage> history = chatMessageMapper.getHistory(nickname, toNickname);

        return R.ok().put(history);
    }



}
