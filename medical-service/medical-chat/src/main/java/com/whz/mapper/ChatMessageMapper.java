package com.whz.mapper;

import com.whz.entity.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-24
 */
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    List<ChatMessage> getHistory(String nickname,String toNickname);

}
