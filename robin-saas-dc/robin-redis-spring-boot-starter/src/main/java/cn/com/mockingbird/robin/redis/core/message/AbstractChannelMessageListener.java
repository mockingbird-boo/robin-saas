package cn.com.mockingbird.robin.redis.core.message;

import cn.com.mockingbird.robin.common.util.ClassUtils;
import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 抽象的消息监听器
 *
 * @author zhaopeng
 * @date 2023/11/21 13:10
 **/
public abstract class AbstractChannelMessageListener<T extends AbstractChannelMessage> implements MessageListener {

    /**
     * 消息类型
     */
    private final Class<T> messageType;

    /**
     * 消息频道
     */
    @Getter
    private final String channel;


    @SneakyThrows
    protected AbstractChannelMessageListener() {
        this.messageType = getMessageClass();
        this.channel = messageType.getDeclaredConstructor().newInstance().getChannel();
    }

    @Override
    public final void onMessage(Message message, byte[] bytes) {
        T messageObj = JSON.parseObject(message.getBody(), messageType);
        this.onMessage(messageObj);
    }

    /**
     * 处理消息
     *
     * @param message 消息
     */
    public abstract void onMessage(T message);

    /**
     * 通过解析类上的泛型，获得消息类型
     * @return 消息类型
     */
    @SuppressWarnings("unchecked")
    private Class<T> getMessageClass() {
        return (Class<T>) ClassUtils.getClassT(this, 0);
    }

}
