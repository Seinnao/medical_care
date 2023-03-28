package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.ChatMessage;
import com.whz.feign.ChatUserFeign;
import com.whz.mapper.ChatMessageMapper;
import com.whz.service.IChatMessageService;
import com.whz.storage.ChatData;
import com.whz.storage.SessionMap;
import com.whz.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/history/{nickname}/{toNickname}")
    public R getHistory(@PathVariable("nickname") String nickname,
                        @PathVariable("toNickname") String toNickname) {
        List<ChatMessage> history = chatMessageMapper.getHistory(nickname, toNickname);

        //遍历消息当信息接收者为自己时，代表已经阅读信息
        history.forEach(msg -> {
            if(msg.getReach().equals(nickname)){
                msg.setState(true);
            }
        });

        chatMessageService.updateBatchById(history);

        return R.ok().put(history);
    }

    @GetMapping("/isOnline/{nickname}")
    public R isOnline(@PathVariable("nickname") String nickname){
        ChatData chatData = SessionMap.sessionMap.get(nickname);
        if(null == chatData){
            return R.ok().put(false);
        }
        return R.ok().put(true);
    }

    @PostMapping
    public R save(@RequestBody ChatMessage chatMessage) {
        ChatMessage one = chatMessageService.getOne(Wrappers.<ChatMessage>lambdaQuery().eq(ChatMessage::getId, chatMessage.getId()));

        if(null == one){
            chatMessageService.save(chatMessage);
        }else{
            chatMessageService.updateById(chatMessage);
        }

        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(chatMessageService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(chatMessageService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(chatMessageService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String nickname){

        LambdaQueryWrapper<ChatMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(ChatMessage::getId);

        if (!"".equals(nickname)) {
            wrapper.like(ChatMessage::getCome,nickname).or().like(ChatMessage::getReach,nickname);
        }

        Page<ChatMessage> page = chatMessageService.page(new Page<>(pageNum, pageSize),wrapper);
        return R.ok().put("data",page);
    }


}
