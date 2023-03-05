package com.whz.service;

import com.whz.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文辉正
 * @since 2023-03-05
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
