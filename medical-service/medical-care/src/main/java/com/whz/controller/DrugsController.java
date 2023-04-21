package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Drugs;
import com.whz.service.IDrugsService;
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
@RequestMapping("/drugs")
public class DrugsController {

    @Resource
    IDrugsService drugsService;

    @PostMapping
    public R save(@RequestBody Drugs drugs) {
        drugsService.saveOrUpdate(drugs);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(drugsService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(drugsService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(drugsService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String label,
                      @RequestParam(defaultValue = "") String name){

        LambdaQueryWrapper<Drugs> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(!StringUtils.isEmpty(label),Drugs::getLabel,label);
        wrapper.like(!StringUtils.isEmpty(name),Drugs::getName,name);
        wrapper.orderByDesc(Drugs::getId);

        Page<Drugs> page = drugsService.page(new Page<>(pageNum, pageSize),wrapper);

        return R.ok().put("data",page);
    }

    @GetMapping("/findLinkName/{name}")
    public R findLinkName(@PathVariable String name){
        List<Drugs> list = drugsService.selectListByName(name);
        return R.ok().put(list);
    }

    @GetMapping("/findById/{id}")
    public R findById(@PathVariable Long id) {
        Drugs drugs = drugsService.selectListById(id);
        return R.ok().put(drugs);
    }

}
