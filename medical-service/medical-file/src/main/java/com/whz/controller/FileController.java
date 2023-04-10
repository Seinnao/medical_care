package com.whz.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whz.entity.File;
import com.whz.exception.ServiceException;
import com.whz.mapper.FileMapper;
import com.whz.utils.FastDfsUtils;
import com.whz.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileMapper fileMapper;

    @Resource
    FastDfsUtils dfsUtils;
    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);

        long size = file.getSize();

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        File dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到FastDfs
            url = dfsUtils.uploadFile(file);
            log.info("上传地址：[{}]",url);
        }

        //如果文件存在则不加入数据库
        File twoFile = fileMapper.selectOne(new QueryWrapper<File>().eq("url",url));

        if(null == twoFile){
            // 存储数据库
            File saveFile = new File();
            saveFile.setName(originalFilename);
            saveFile.setType(type);
            saveFile.setSize(size/1024); // 单位 kb
            saveFile.setUrl(url);
            saveFile.setMd5(md5);
            fileMapper.insert(saveFile);
        }

        return R.ok().put("url",url);
    }

    /**
     * 文件下载接口   http://localhost:8080/file/download/{url}
     * @throws IOException
     */
    @GetMapping("/download/**")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();

        //对路径进行处理
        String url = request.getRequestURI().replace("/file/download/","");

        //根据url获取文件名
        File file = fileMapper.selectOne(new QueryWrapper<File>().eq("url", url));

        if(null == file){
            throw new ServiceException("地址数据错误！");
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        try {
            os.write(dfsUtils.downloadFile(url));
        } catch (Exception e) {
            log.info("文件下载失败，文件不存在");
        }
        os.flush();
        os.close();
    }


    /**
     * 通过文件的md5查询文件
     * @param md5
     */
    private File getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<File> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @PostMapping("/update")
    public R update(@RequestBody File files) {
        return R.ok().put(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        File files = fileMapper.selectById(id);
        files.setIsDelete(true);
        try {
            dfsUtils.deleteFile(files.getUrl());
        } catch (Exception e) {
            throw new ServiceException("文件删除失败！");
        }
        fileMapper.deleteById(files.getId());
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<File> files = fileMapper.selectList(queryWrapper);
        for (File file : files) {
            file.setIsDelete(true);
            //文件删除
            try {
                dfsUtils.deleteFile(file.getUrl());
            } catch (Exception e) {
                throw new ServiceException("文件删除失败！");
            }
            fileMapper.deleteById(file.getId());
        }
        return R.ok();
    }

    /**
     * 分页查询接口
     */
    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return R.ok().put(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }




}