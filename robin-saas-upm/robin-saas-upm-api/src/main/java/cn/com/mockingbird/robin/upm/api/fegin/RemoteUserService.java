package cn.com.mockingbird.robin.upm.api.fegin;

import cn.com.mockingbird.robin.common.util.response.ResponseData;
import cn.com.mockingbird.robin.upm.api.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import static cn.com.mockingbird.robin.upm.api.fegin.RemoteUserService.UPM_SERVICE;

/**
 * 用户 API 接口
 *
 * @author zhaopeng
 * @date 2023/11/27 16:58
 **/
@FeignClient(contextId = "remoteUserService", value = UPM_SERVICE)
public interface RemoteUserService {

    String UPM_SERVICE = "upm-service";

    /**
     * 根据 用户名/邮箱/手机号 查询用户信息
     * @param username 用户名/邮箱/手机号
     * @return 用户信息
     */
    @GetMapping("/user/{username}")
    ResponseData<UserInfo> getUser(@PathVariable("username") String username);

    /**
     * 锁定用户
     * @param username 用户名
     * @return true - 成功锁定
     */
    @PutMapping("/user/{username}")
    ResponseData<Boolean> disableUser(@PathVariable("username") String username);
}
