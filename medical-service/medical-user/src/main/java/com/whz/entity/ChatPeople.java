package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("chat_people")
public class ChatPeople implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String otherParty;

    private Integer unreadMsg;

    private Date time;

}
