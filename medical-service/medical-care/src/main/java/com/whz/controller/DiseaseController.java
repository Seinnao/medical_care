package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Disease;
import com.whz.mapper.SymptomMapper;
import com.whz.service.IDiseaseService;
import com.whz.utils.R;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/disease")
public class DiseaseController {


    @Resource
    IDiseaseService diseaseService;

    @Resource
    SymptomMapper symptomMapper;
    
    @PostMapping
    public R save(@RequestBody Disease disease) {
        diseaseService.saveOrUpdateChange(disease);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(diseaseService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(diseaseService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(diseaseService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String name){

        LambdaQueryWrapper<Disease> wrapper = Wrappers.lambdaQuery();
        wrapper.like(!StringUtils.isEmpty(name),Disease::getName,name);
        wrapper.orderByDesc(Disease::getId);

        Page<Disease> page = diseaseService.page(new Page<>(pageNum, pageSize),wrapper);
        page.getRecords().forEach(disease ->{
            disease.setSymptom(symptomMapper.selectByDisease(disease));
        });
        return R.ok().put("data",page);
    }



}
