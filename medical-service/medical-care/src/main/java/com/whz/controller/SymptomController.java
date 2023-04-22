package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.whz.entity.Symptom;
import com.whz.mapper.DrugsMapper;
import com.whz.service.ISymptomService;
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
@RequestMapping("/symptom")
public class SymptomController {

    @Resource
    ISymptomService symptomService;

    @Resource
    DrugsMapper drugsMapper;

    @PostMapping
    public R save(@RequestBody Symptom symptom) {
        CustomDictionary.add(symptom.getName());
        symptomService.saveOrUpdateChange(symptom);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(symptomService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(symptomService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(symptomService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String name){

        LambdaQueryWrapper<Symptom> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Symptom::getId);
        wrapper.like(!StringUtils.isEmpty(name), Symptom::getName,name);

        Page<Symptom> page = symptomService.page(new Page<>(pageNum, pageSize),wrapper);
        page.getRecords().forEach(symptom -> {
            symptom.setDrugs(drugsMapper.selectListBySymptom(symptom.getId()));
        });
        return R.ok().put("data",page);
    }

    @GetMapping("/findLinkName/{name}")
    public R findLinkName(@PathVariable String name){
        LambdaQueryWrapper<Symptom> wrapper = Wrappers.lambdaQuery();
        wrapper.like(!StringUtils.isEmpty(name),Symptom::getName,name);
        wrapper.orderByDesc(Symptom::getId);
        List<Symptom> list = symptomService.list(wrapper);
        return R.ok().put(list);
    }

}
