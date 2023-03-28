package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 文辉正
 * @since 2023/3/22 16:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_message")
public class ChatMessage {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String come;//发送者
    private String reach;//接收者
    private String type;
    private String content;
    private Date   time;
    private Boolean state;
    private String mediaUrl; //媒体图片的路径


}
