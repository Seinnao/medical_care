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
@TableName("medicine_drugs")
public class Drugs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String describe;

    private String imagesUrl;

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
    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    @Override
    public String toString() {
        return "Drugs{" +
            "id=" + id +
            ", name=" + name +
            ", describe=" + describe +
            ", imagesUrl=" + imagesUrl +
        "}";
    }
}
