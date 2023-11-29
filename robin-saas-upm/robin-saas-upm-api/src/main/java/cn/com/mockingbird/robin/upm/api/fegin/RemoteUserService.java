package cn.com.mockingbird.robin.upm.api.fegin;

import cn.com.mockingbird.robin.upm.api.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PathVariable;

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

    UserInfo getUser();

    Boolean disableUser(@PathVariable("username") String username);
}
