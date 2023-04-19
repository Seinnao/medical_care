package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.CaseRecord;
import com.whz.service.ICaseRecordService;
import com.whz.utils.R;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/case-record")
public class CaseRecordController {


    @Resource
    ICaseRecordService caseRecordService;

    @PostMapping
    public R save(@RequestBody CaseRecord caseRecord) {
        if(null == caseRecord.getId()){
            caseRecord.setTime(new Date());
        }
        caseRecordService.saveOrUpdate(caseRecord);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(caseRecordService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(caseRecordService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(caseRecordService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String doctor,
                      @RequestParam(defaultValue = "") String name){

        LambdaQueryWrapper<CaseRecord> wrapper = Wrappers.lambdaQuery();
        wrapper.like(!StringUtils.isEmpty(doctor),CaseRecord::getDoctor,doctor);
        wrapper.like(!StringUtils.isEmpty(name),CaseRecord::getName,name);
        wrapper.orderByDesc(CaseRecord::getId);

        Page<CaseRecord> page = caseRecordService.page(new Page<>(pageNum, pageSize),wrapper);

        return R.ok().put("data",page);
    }

}
