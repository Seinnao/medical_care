package com.whz.service.impl;

import com.whz.entity.File;
import com.whz.mapper.FileMapper;
import com.whz.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
