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
@TableName("drugs_symptom")
public class DrugsSymptom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long drugsId;

    private Long symptomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(Long drugsId) {
        this.drugsId = drugsId;
    }
    public Long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Long symptomId) {
        this.symptomId = symptomId;
    }

    @Override
    public String toString() {
        return "DrugsSymptom{" +
            "id=" + id +
            ", drugsId=" + drugsId +
            ", symptomId=" + symptomId +
        "}";
    }
}
