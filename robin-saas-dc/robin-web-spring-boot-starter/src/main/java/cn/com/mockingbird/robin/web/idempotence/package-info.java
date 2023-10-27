/**
 * 这是幂等性模块，实现了接口的防重放功能，保护接口业务逻辑处理过程的数据安全。
 * 支持的幂等策略有两种：锁（默认）和幂等令牌。{@link cn.com.mockingbird.robin.web.idempotence.Idempotent.Strategy}
 * 默认的锁策略主要是通过对“用户名 + 客户端IP + 类名 + 方法名”加一把指定过期时间（默认30s）的锁，来保证指定时间内某用户只能请求接口一次。
 * 而 token 策略是请求之前，先获取幂等令牌，真正请求的时候携带幂等令牌。
 *
 * @author zhaopeng
 * @date 2023/10/16 22:27
 **/
package cn.com.mockingbird.robin.web.idempotence;