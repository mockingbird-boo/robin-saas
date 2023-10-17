package cn.com.mockingbird.robin.web.idempotence;

import cn.com.mockingbird.robin.common.user.UserHolder;
import cn.com.mockingbird.robin.redis.core.service.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 令牌服务
 *
 * @author zhaopeng
 * @date 2023/10/16 22:43
 **/
@RestController
@RequestMapping("/idempotent-token")
public class IdempotentTokenController {

    private final RedisService redisService;

    public IdempotentTokenController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping
    public String getToken() {
        String username = UserHolder.getCurrentUser().getUsername();
        // 使用随机32位字符串作为 token
        String token = RandomStringUtils.randomAlphabetic(32);
        String key = Key.generateTokenKey(username, token);
        // 令牌有效时间 30 min
        redisService.setExpire(key, Thread.currentThread().getName(), 30, TimeUnit.MINUTES);
        return token;
    }

}
