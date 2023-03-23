package com.whz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whz.entity.File;
import com.whz.mapper.FileMapper;
import com.whz.utils.FastDfsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 文辉正
 * @since 2023/3/23 15:06
 */
@Slf4j
@Controller
@RequestMapping("/images")
public class ImageController {

    @Resource
    FastDfsUtils dfsUtils;

    @Resource
    private FileMapper fileMapper;

    @GetMapping("/**")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //对路径进行处理
        String url = request.getRequestURI().replace("/images/","");

        //根据url获取文件名
        File file = fileMapper.selectOne(new QueryWrapper<File>().eq("url", url));

        response.setHeader("Cache-Control", "no-store, no-cache");

        if(file.getType().equals("png")){
            response.setContentType("image/png");
        }else{
            response.setContentType("image/jpeg");
        }

        //设置输出流的格式
        try(ServletOutputStream os = response.getOutputStream()){
            try {
                os.write(dfsUtils.downloadFile(url));
            } catch (Exception e) {
                log.info("文件下载失败，文件不存在");
            }
        }
    }

}
