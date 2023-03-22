package com.whz.dto;

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
public class ChatMessage {

    private String from;//发送者
    private String to;//接收者
    private String type;
    private String content;
    private Date time;
    private byte[] media;

}
