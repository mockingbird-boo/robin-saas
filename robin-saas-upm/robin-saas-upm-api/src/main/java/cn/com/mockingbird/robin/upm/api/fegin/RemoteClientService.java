package cn.com.mockingbird.robin.upm.api.fegin;

import cn.com.mockingbird.robin.common.util.response.ResponseData;
import cn.com.mockingbird.robin.upm.api.entity.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static cn.com.mockingbird.robin.upm.api.fegin.RemoteUserService.UPM_SERVICE;

/**
 * 客户端 API 接口
 *
 * @author zhaopeng
 * @date 2023/12/2 2:08
 **/
@FeignClient(contextId = "remoteClientService", value = UPM_SERVICE)
public interface RemoteClientService {

    /**
     * 查询所有客户端
     * @return 所有客户端集合
     */
    @GetMapping("/clients")
    ResponseData<List<Client>> getClients();

    /**
     * 根据 clientId 查询客户端
     * @param clientId 客户端 ID
     * @return 客户端信息
     */
    @GetMapping("/client/{clientId}")
    ResponseData<Client> getClientById(@PathVariable("clientId") String clientId);

}
