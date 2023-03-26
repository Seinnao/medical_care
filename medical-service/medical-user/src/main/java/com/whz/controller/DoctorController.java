package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Doctor;
import com.whz.mapper.DoctorMapper;
import com.whz.service.IDoctorService;
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
 * @since 2023-03-26
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    DoctorMapper doctorMapper;

    @Resource
    IDoctorService doctorService;

    @GetMapping("/getDoctors")
    public R getDoctors(@RequestParam Integer pageNum,
                        @RequestParam Integer pageSize){

        Page<Doctor> page = new Page<>(pageNum, pageSize);
        IPage<Doctor> list = doctorMapper.getDoctorList(page);

        return R.ok().put(list);
    }

    @PostMapping
    public R save(@RequestBody Doctor doctor) {
        Doctor one = doctorService.getOne(Wrappers.<Doctor>lambdaQuery().eq(Doctor::getId, doctor.getId()));

        if(null == one){
            doctorService.save(doctor);
        }else{
            doctorService.updateById(doctor);
        }

        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(doctorService.removeById(id));
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.ok().put(doctorService.removeByIds(ids));
    }

    @GetMapping
    public R findAll() {
        return R.ok().put(doctorService.list());
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam(defaultValue = "") String nickname){

        LambdaQueryWrapper<Doctor> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Doctor::getId);

//        if (!"".equals(nickname)) {
//            wrapper.like(Doctor::getCome,nickname).or().like(Doctor::getReach,nickname);
//        }

        Page<Doctor> page = doctorService.page(new Page<>(pageNum, pageSize),wrapper);
        return R.ok().put("data",page);
    }



}
