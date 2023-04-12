package com.whz.service;

import com.whz.entity.ChatPeople;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-28
 */
public interface IChatPeopleService extends IService<ChatPeople> {

    List<ChatPeople> findAllByNickname(String name);
}
