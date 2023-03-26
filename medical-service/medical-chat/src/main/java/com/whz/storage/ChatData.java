package com.whz.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;
/**
 * @author 文辉正
 * @since 2023/3/22 17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatData {

    private Session session;

    private String id;

    private String nickname;

    private String role;

}
