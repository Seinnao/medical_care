package com.whz.controller;

import com.whz.entity.ChatMessage;
import com.whz.feign.ChatUserFeign;
import com.whz.service.IChatMessageService;
import com.whz.storage.ChatData;
import com.whz.storage.SessionMap;
import com.whz.utils.JsonUtils;
import com.whz.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author 文辉正
 * @since 2023/3/22 16:54
 */
@Component //注册到容器中
@ServerEndpoint("/socket/{nickname}/{role}")  //接收websocket请求路径
@Slf4j
public class ChatController {

    private static IChatMessageService chatMessageService;
    private static ChatUserFeign chatUserFeign;
    private static RedisUtil redisUtil;

    //只能如此装配 因为webSocket会启动多线程
    @Resource
    public void setChatMessageService(IChatMessageService chatMessageService){
        ChatController.chatMessageService = chatMessageService;
    }

    @Resource
    public void setChatUserFeign(ChatUserFeign chatUserFeign){
        ChatController.chatUserFeign = chatUserFeign;
    }

    @Resource
    public void setRedisUtil(RedisUtil redisUtil){
        ChatController.redisUtil = redisUtil;
    }


    //处理连接建立
    @OnOpen
    public void opOpen(Session session,
                       @PathParam("nickname") String nickname,
                       @PathParam("role") String role) {

        log.info("【有新的客户端连接了】：{}", session.getId());

        ChatData chatData = ChatData.builder().
                session(session)
                .id(session.getId())
                .nickname(nickname)
                .role(role)
                .build();

//        User user = chatUserFeign.findByNickname(nickname);
//        if(null == user){
//            throw new ServiceException("连接异常");
//        }

        if(role.equals("ROLE_DOCTOR")){
            //Doctor doctor = chatUserFeign.findByName(nickname);
            SessionMap.sessionDoctorMap.put(nickname,chatData);
        }else {
            //if(role.equals("ROLE_USER"))
            SessionMap.sessionUserMap.put(nickname,chatData);
        }

        log.info("【websocket消息】有新的连接，总数：{}", SessionMap.sessionUserMap.size()+SessionMap.sessionDoctorMap.size());
    }

    //处理连接关闭
    @OnClose
    public void Onclose(@PathParam("nickname") String nickname,
                        @PathParam("role") String role) {
        if(role.equals("ROLE_DOCTOR")){
            SessionMap.sessionDoctorMap.remove(nickname);
        }else {
            SessionMap.sessionUserMap.remove(nickname);
        }

        log.info("【websocket消息】{}连接断开，总数：{}", nickname, SessionMap.sessionUserMap.size()+SessionMap.sessionDoctorMap.size());
    }

    @OnMessage
    public void onMessage(String message,
                          @PathParam("nickname") String nickname,
                          @PathParam("role") String role){
        log.info("【websocket消息】收到客户端发来的文本消息：{}", message);

        ChatMessage chatMessage = JsonUtils.fromJson(message, ChatMessage.class);

        ChatData chatData = null;
        //医生去用户拿session，用户去医生拿session
        if(role.equals("ROLE_DOCTOR")){
            chatData = SessionMap.sessionUserMap.get(chatMessage.getReach());
        }else{
            chatData = SessionMap.sessionDoctorMap.get(chatMessage.getReach());
        }

        if(null != chatData){
            forwardMessage(chatData,message);
        }

        chatMessage.setState(false);
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
