package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Dict;
import com.whz.entity.Menu;
import com.whz.service.IDictService;
import com.whz.service.IMenuService;
import com.whz.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private IDictService dictService;

    // 新增或者更新
    @PostMapping
    public R save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return R.ok();
    }

    @GetMapping("/ids")
    public R findAllIds() {
        return R.ok().put(menuService.list().stream().map(Menu::getId));
    }

    @GetMapping
    public R findAll(@RequestParam(defaultValue = "") String name) {
        return R.ok().put(menuService.findMenus(name));
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok().put(menuService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam String name,
                      @RequestParam Integer pageNum,
                      @RequestParam Integer pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return R.ok().put(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/icons")
    public R getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "icon");
        return R.ok().put(dictService.selectList(queryWrapper));
    }

}
