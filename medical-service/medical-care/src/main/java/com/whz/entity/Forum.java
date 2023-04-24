package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-04-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("medicine_forum")
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long userId;

    private String title;

    private Integer see;

    private String content;

    private String outline;

    private Date time;

    @TableField(exist = false)
    private Integer collection;

    @TableField(exist = false)
    private Integer commentSum;

    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private String nickname;

}
