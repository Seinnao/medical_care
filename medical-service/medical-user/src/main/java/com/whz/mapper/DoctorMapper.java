package com.whz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.Doctor;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-26
 */
public interface DoctorMapper extends BaseMapper<Doctor> {

    IPage<Doctor> getDoctorList(Page<Doctor> page,String name);

}
