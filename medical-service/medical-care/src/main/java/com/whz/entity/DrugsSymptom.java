package com.whz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-04-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("drugs_symptom")
public class DrugsSymptom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long drugsId;

    private Long symptomId;

}
