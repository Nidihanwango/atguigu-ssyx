package com.atguigu.ssyx.client.user;

import com.atguigu.ssyx.common.config.FeignConfig;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user", configuration = {FeignConfig.class})
public interface UserFeignClient {
    @GetMapping("/api/user/leader/inner/getUserAddressByUserId/{userId}")
    LeaderAddressVo getUserAddressByUserId(@PathVariable(value = "userId") Long userId);
}
