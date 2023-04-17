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
@TableName("disease_symptom")
public class DiseaseSymptom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long diseaseId;

    private Long symptomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }
    public Long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Long symptomId) {
        this.symptomId = symptomId;
    }

    @Override
    public String toString() {
        return "DiseaseSymptom{" +
            "id=" + id +
            ", diseaseId=" + diseaseId +
            ", symptomId=" + symptomId +
        "}";
    }
}
