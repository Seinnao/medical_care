package com.whz.feign.hystrix;

import com.whz.entity.Doctor;
import com.whz.entity.User;
import com.whz.feign.ChatUserFeign;
import com.whz.utils.R;
import org.springframework.stereotype.Component;

/**
 * @author 文辉正
 * @since 2023/3/24 17:20
 */
@Component
public class ChatUserFeignHystrix implements ChatUserFeign {

    @Override
    public Doctor findByName(String name) {
        return null;
    }

    @Override
    public R findByUsername(String username) {
        return R.error();
    }

    @Override
    public User findByNickname(String nickname) {
        return null;
    }
}
