package com.whz.config;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.whz.entity.Disease;
import com.whz.entity.Drugs;
import com.whz.entity.Symptom;
import com.whz.service.IDiseaseService;
import com.whz.service.IDrugsService;
import com.whz.service.ISymptomService;
import com.whz.strings.WordType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 文辉正
 * @since 2023/4/22 21:17
 */
@Configuration
public class HanLPConfig {

    @Resource
    IDrugsService drugsService;

    @Resource
    ISymptomService symptomService;

    @Resource
    IDiseaseService diseaseService;


    @Bean
    Segment segment(){
        //数据库存储查询
        List<Drugs> drugs = drugsService.list();
        //加入分词
        drugs.forEach( drug -> {
            CustomDictionary.add(drug.getName(), WordType.drugs);
        });
        List<Symptom> symptoms = symptomService.list();
        symptoms.forEach(symptom -> {
            CustomDictionary.add(symptom.getName(), WordType.symptom);
        });
        List<Disease> diseases = diseaseService.list();
        diseases.forEach(disease ->{
            CustomDictionary.add(disease.getName(), WordType.disease);
        });
        return HanLP.newSegment();
    }

}
