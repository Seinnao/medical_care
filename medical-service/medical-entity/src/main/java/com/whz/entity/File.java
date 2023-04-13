package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小(kb)
     */
    private Long size;

    /**
     * 下载链接
     */
    private String url;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 是否禁用链接
     */
    private Boolean enable;

    /**
     * 用途
     * */
    private String purpose;

}
