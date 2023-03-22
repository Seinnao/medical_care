package com.whz.controller;

import com.whz.dto.ChatMessage;
import com.whz.storage.ChatData;
import com.whz.storage.SessionMap;
import com.whz.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@Component  //注册到容器中
@ServerEndpoint("/socket/{username}")  //接收websocket请求路径
@Slf4j
public class ChatController {

    //处理连接建立
    @OnOpen
    public void opOpen(Session session,@PathParam("username") String username) {
        log.info("【有新的客户端连接了】：{}", session.getId());

        ChatData chatData = ChatData.builder().
                session(session)
                .id(session.getId())
                .username(username)
                .build();

        SessionMap.sessionMap.put(username,chatData);

        log.info("【websocket消息】有新的连接，总数：{}", SessionMap.sessionMap.size());
    }

    //处理连接关闭
    @OnClose
    public void Onclose(@PathParam("username") String username) {
        SessionMap.sessionMap.remove(username);
        log.info("【websocket消息】{}连接断开，总数：{}", username, SessionMap.sessionMap.size());
    }

    @OnMessage
    public void onMessage(String message,@PathParam("username") String username){
        log.info("【websocket消息】收到客户端发来的文本消息：{}", message);

        ChatMessage chatMessage = JsonUtils.fromJson(message, ChatMessage.class);

        //log.info("{}/n/t{}",chatMessage,JsonUtils.toJson(chatMessage));

        if(chatMessage.getTo().equals("all")){
            Collection<ChatData> values = SessionMap.sessionMap.values();
            for (ChatData chatData: values){
                //转发消息
                forwardMessage(chatData,message);
            }
        }else {
            //获取转发对象session
            ChatData chatData = SessionMap.sessionMap.get(chatMessage.getTo());
            //转发消息
            forwardMessage(chatData,message);
        }
        //保存到数据库


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
