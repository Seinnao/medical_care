package com.whz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whz.entity.DrugsSymptom;
import com.whz.entity.Symptom;
import com.whz.mapper.DrugsSymptomMapper;
import com.whz.mapper.SymptomMapper;
import com.whz.service.ISymptomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class SymptomServiceImpl extends ServiceImpl<SymptomMapper, Symptom> implements ISymptomService {

    @Resource
    DrugsSymptomMapper drugsSymptomMapper;

    @Override
    public void saveOrUpdateChange(Symptom symptom) {
        if(null == symptom.getId()){
            long id = IdWorker.getId();
            symptom.setId(id);
            this.save(symptom);
            List<DrugsSymptom> list = new ArrayList<>();
            symptom.getDrugsIds().forEach(drugsId ->{
                list.add(DrugsSymptom.builder()
                        .id(IdWorker.getId())
                        .symptomId(id)
                        .drugsId(drugsId)
                        .build());
            });
            drugsSymptomMapper.insertBatch(list);
        }else {
            this.updateById(symptom);
            List<DrugsSymptom> list = drugsSymptomMapper.selectList(Wrappers
                    .<DrugsSymptom>lambdaQuery()
                    .eq(DrugsSymptom::getSymptomId, symptom.getId()));

            boolean repeat;
            //更新
            for (Long drugsId : symptom.getDrugsIds()) {
                repeat = false;
                for(DrugsSymptom drugsSymptom: list){
                    if(drugsId.equals(drugsSymptom.getDrugsId())){
                        repeat = true;
                        list.remove(drugsSymptom);
                        break;
                    }
                }
                if(!repeat){
                    drugsSymptomMapper.insert(DrugsSymptom.builder()
                            .id(IdWorker.getId())
                            .symptomId(symptom.getId())
                            .drugsId(drugsId)
                            .build());
                }
            }
            //删除多余
            list.forEach(drugsSymptom -> {
                drugsSymptomMapper.deleteById(drugsSymptom);
            });
        }

    }
}
