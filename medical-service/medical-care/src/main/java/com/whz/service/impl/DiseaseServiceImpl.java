package com.whz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whz.entity.Disease;
import com.whz.entity.DiseaseSymptom;
import com.whz.mapper.DiseaseMapper;
import com.whz.mapper.DiseaseSymptomMapper;
import com.whz.service.IDiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements IDiseaseService {

    @Resource
    DiseaseSymptomMapper diseaseSymptomMapper;

    @Override
    public void saveOrUpdateChange(Disease disease) {
        if(null == disease.getId()){
            long id = IdWorker.getId();
            disease.setId(id);
            boolean save = this.save(disease);
            if(save){
                //插人数据
                disease.getSymptomIds().forEach(symptomId ->{
                    diseaseSymptomMapper.insert(DiseaseSymptom
                            .builder().symptomId(symptomId)
                            .diseaseId(id)
                            .build());
                });
            }
        }else {
            this.updateById(disease);
            List<DiseaseSymptom> list = diseaseSymptomMapper.selectList(Wrappers
                    .<DiseaseSymptom>lambdaQuery()
                    .eq(DiseaseSymptom::getDiseaseId, disease.getId()));
            boolean repeat;
            for(Long symptomId : disease.getSymptomIds()){
                repeat = false;
                for(DiseaseSymptom diseaseSymptom : list){
                    if(symptomId.equals(diseaseSymptom.getSymptomId())){
                        repeat = true;
                        list.remove(diseaseSymptom);
                        break;
                    }
                }
                if(!repeat){
                    diseaseSymptomMapper.insert(DiseaseSymptom
                            .builder().symptomId(symptomId)
                            .diseaseId(disease.getId())
                            .build());
                }
            }
            list.forEach(diseaseSymptom ->{
                diseaseSymptomMapper.deleteById(diseaseSymptom);
            });
        }


    }
}
