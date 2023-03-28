package com.whz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whz.entity.ChatMessage;
import com.whz.entity.User;
import com.whz.exception.ServiceException;
import com.whz.feign.ChatUserFeign;
import com.whz.service.IChatMessageService;
import com.whz.storage.ChatData;
import com.whz.storage.SessionMap;
import com.whz.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;

/**
 * @author 文辉正
 * @since 2023/3/22 16:54
 */
@Component //注册到容器中
@ServerEndpoint("/socket/{nickname}")  //接收websocket请求路径
@Slf4j
public class ChatController {


    private static IChatMessageService chatMessageService;
    private static ChatUserFeign chatUserFeign;

    //只能如此装配 因为webSocket会启动多线程
    @Resource
    public void setChatMessageService(IChatMessageService chatMessageService){
        ChatController.chatMessageService = chatMessageService;
    }


    @Resource
    public void setChatUserFeign(ChatUserFeign chatUserFeign){
        ChatController.chatUserFeign = chatUserFeign;
    }


    //处理连接建立
    @OnOpen
    public void opOpen(Session session,@PathParam("nickname") String nickname) {
        log.info("【有新的客户端连接了】：{}", session.getId());

        User user = chatUserFeign.findByNickname(nickname);
        if(null == user){
            throw new ServiceException("连接异常");
        }

        ChatData chatData = ChatData.builder().
                session(session)
                .id(session.getId())
                .nickname(nickname)
                .role(user.getRole())
                .build();

        SessionMap.sessionMap.put(nickname,chatData);

        log.info("【websocket消息】有新的连接，总数：{}", SessionMap.sessionMap.size());
    }

    //处理连接关闭
    @OnClose
    public void Onclose(@PathParam("nickname") String nickname) {
        SessionMap.sessionMap.remove(nickname);
        log.info("【websocket消息】{}连接断开，总数：{}", nickname, SessionMap.sessionMap.size());
    }

    @OnMessage
    public void onMessage(String message,@PathParam("nickname") String nickname){
        log.info("【websocket消息】收到客户端发来的文本消息：{}", message);

        ChatMessage chatMessage = JsonUtils.fromJson(message, ChatMessage.class);




        if(chatMessage.getReach().equals("all")){
            Collection<ChatData> values = SessionMap.sessionMap.values();
            for (ChatData chatData: values){
                //转发消息
                forwardMessage(chatData,message);
            }
        }else {
            //获取转发对象session
            ChatData chatData = SessionMap.sessionMap.get(chatMessage.getReach());
            //转发消息
            forwardMessage(chatData,message);
        }

        //保存到数据库
        chatMessageService.save(chatMessage);
    }

    private void forwardMessage(ChatData chatData,String message){
        //转发消息
        try {
            chatData.getSession().getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
