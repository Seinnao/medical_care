package com.whz.service.impl;

import com.whz.entity.ChatPeople;
import com.whz.mapper.ChatPeopleMapper;
import com.whz.service.IChatPeopleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-28
 */
@Service
public class ChatPeopleServiceImpl extends ServiceImpl<ChatPeopleMapper, ChatPeople> implements IChatPeopleService {

    @Override
    public List<ChatPeople> findAllByNickname(String name) {
        List<ChatPeople> list = this.baseMapper.findAllByNickname(name);
        //对集合数据进行处理
        ArrayList<ChatPeople> chatPeoples = new ArrayList<>();
        list.forEach( chatPeople -> {
            if(chatPeople.getNickname().equals(name)){
                for (ChatPeople people : list) {
                    if (chatPeople.getOtherParty().equals(people.getNickname())) {
                        if (null == chatPeople.getTime() ||
                                (null != people.getTime() && chatPeople.getTime().compareTo(people.getTime()) < 0)) {
                            chatPeople.setTime(people.getTime());
                            chatPeople.setContent(people.getContent());
                        }
                        chatPeoples.add(chatPeople);
                        break;
                    }
                }
            }
        });
        return chatPeoples;
    }
}
