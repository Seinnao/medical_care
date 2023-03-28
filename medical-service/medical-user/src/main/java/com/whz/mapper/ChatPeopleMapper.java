package com.whz.mapper;

import com.whz.entity.ChatPeople;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-28
 */
public interface ChatPeopleMapper extends BaseMapper<ChatPeople> {


    List<ChatPeople> findAllByNickname(@Param("name") String name);

}
