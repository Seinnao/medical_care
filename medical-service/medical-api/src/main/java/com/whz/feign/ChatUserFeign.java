package com.whz.feign;

import com.whz.feign.hystrix.ChatUserFeignHystrix;
import com.whz.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 文辉正
 * @since 2023/3/24 17:05
 */
@FeignClient(value = "user-service",fallback = ChatUserFeignHystrix.class) //提供者名称
public interface ChatUserFeign {

    @GetMapping("/user/username/{username}")
    public R findByUsername(@PathVariable String username);

}
