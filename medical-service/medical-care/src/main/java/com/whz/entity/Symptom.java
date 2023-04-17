package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
@TableName("medicine_symptom")
public class Symptom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String describe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Symptom{" +
            "id=" + id +
            ", name=" + name +
            ", describe=" + describe +
        "}";
    }
}
