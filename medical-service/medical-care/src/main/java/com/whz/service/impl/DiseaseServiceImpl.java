package com.whz.service.impl;

import com.whz.entity.Disease;
import com.whz.mapper.DiseaseMapper;
import com.whz.service.IDiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
