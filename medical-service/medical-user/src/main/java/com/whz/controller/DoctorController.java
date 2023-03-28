package com.whz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Doctor;
import com.whz.entity.UserForDoctor;
import com.whz.mapper.DoctorMapper;
import com.whz.service.IDoctorService;
import com.whz.service.IUserForDoctorService;
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

    @Resource
    IUserForDoctorService userForDoctorService;

    @GetMapping("/getDoctors")
    public R getDoctors(@RequestParam Integer pageNum,
                        @RequestParam Integer pageSize,
                        @RequestParam(defaultValue = "") String name){

        Page<Doctor> page = new Page<>(pageNum, pageSize);
        IPage<Doctor> list = doctorMapper.getDoctorList(page,name);

        return R.ok().put(list);
    }

    @PostMapping
    public R save(@RequestBody Doctor doctor) {
        Doctor one = doctorService.getOne(Wrappers.<Doctor>lambdaQuery().eq(Doctor::getId, doctor.getId()));

        if(null == one){
            //存储医生信息
            long id = IdWorker.getId();
            doctor.setId(id);
            doctor.setIsOnline(true);
            doctor.setState(false);
            doctorService.save(doctor);

            UserForDoctor forDoctor = UserForDoctor
                    .builder()
                    .userId(doctor.getUserId())
                    .doctorId(id)
                    .state(1).build();
            //添加表
            userForDoctorService.save(forDoctor);
        }else{
            doctorService.updateById(doctor);
        }

        return R.ok();
    }

    @PostMapping("/agree")
    public R agreement(@RequestBody Doctor doctor){

        doctor.setState(true);
        doctorService.updateById(doctor);

        userForDoctorService.update(Wrappers.<UserForDoctor>lambdaUpdate()
                .set(UserForDoctor::getState,3)
                .eq(UserForDoctor::getDoctorId,doctor.getId()));

        return R.ok();
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok().put(doctorService.removeById(id));
    }


    @DeleteMapping("/fail/{id}")
    public R deleteFail(@PathVariable String id) {
        doctorService.removeById(id);

        userForDoctorService.update(Wrappers.<UserForDoctor>lambdaUpdate()
                .set(UserForDoctor::getState,4)
                .eq(UserForDoctor::getDoctorId,id));

        return R.ok();
    }

    //reApply/
    @GetMapping("/reApply/{id}")
    public R reApply(@PathVariable Long id) {
        userForDoctorService.remove(Wrappers.<UserForDoctor>lambdaQuery().eq(UserForDoctor::getUserId,id));
        return R.ok();
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
                      @RequestParam Integer pageSize){

        LambdaQueryWrapper<Doctor> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Doctor::getId);

        wrapper.eq(Doctor::getState,false);

        Page<Doctor> page = doctorService.page(new Page<>(pageNum, pageSize),wrapper);

        List<Doctor> doctors = page.getRecords();

        //修改状态
        doctors.forEach(doctor -> userForDoctorService.update(Wrappers.<UserForDoctor>lambdaUpdate()
                .set(UserForDoctor::getState,2)
                .eq(UserForDoctor::getDoctorId,doctor.getId())));

        return R.ok().put("data",page);
    }

    @GetMapping("/applyNumber")
    public R applyNumber(){
        return R.ok().put(doctorMapper.selectCount(Wrappers.<Doctor>lambdaQuery().eq(Doctor::getState, false)));
    }


    @GetMapping("/userId/{userId}")
    public R findByUsername(@PathVariable String userId) {

        UserForDoctor one = userForDoctorService.getOne(
                Wrappers.<UserForDoctor>lambdaQuery()
                        .eq(UserForDoctor::getUserId, userId));

        if(null == one){
            one = new UserForDoctor();
            one.setState(0);
        }
        return R.ok().put(one);
    }




}
