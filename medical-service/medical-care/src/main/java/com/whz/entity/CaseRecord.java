package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-19
 */
@Data
@TableName("case_record")
public class CaseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 医生名字
     */
    private String doctor;

    /**
     * 内容
     */
    private String content;

    /**
     * 记录时间
     */
    private Date time;

}
