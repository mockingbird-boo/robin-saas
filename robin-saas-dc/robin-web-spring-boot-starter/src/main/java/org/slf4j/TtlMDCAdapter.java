package org.slf4j;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.util.BranchUtils;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.helpers.ThreadLocalMapOfStacks;
import org.slf4j.spi.MDCAdapter;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 MDC Adapter
 * <p>
 * 用 TransmittableThreadLocal 替换 ThreadLocal，解决多线程，异步情况下 traceId 可能无法传递问题
 * @author zhaopeng
 * @date 2023/10/15 16:48
 **/
public class TtlMDCAdapter implements MDCAdapter {

    private final ThreadLocal<Map<String, String>> copyOnInheritThreadLocal = new TransmittableThreadLocal<>();

    private static final int WRITE_OPERATION = 1;
    private static final int READ_OPERATION = 2;

    private final static TtlMDCAdapter TTL_MDC_ADAPTER;
    private final static ThreadLocal<Integer> LAST_OP_CONTEXT;

    static {
        TTL_MDC_ADAPTER = new TtlMDCAdapter();
        LAST_OP_CONTEXT = new ThreadLocal<>();
        // 替换 MDC 的 MDCAdapter
        MDC.mdcAdapter = TTL_MDC_ADAPTER;
    }

    /**
     * 返回 MDCAdapter 实例
     * @return MDCAdapter 实例
     */
    @SuppressWarnings("all")
    public static MDCAdapter getInstance() {
        return TTL_MDC_ADAPTER;
    }

    private final ThreadLocalMapOfStacks threadLocalMapOfDeques = new ThreadLocalMapOfStacks();

    @SuppressWarnings("all")
    @Override
    public void put(String key, String val) {
        Assert.notNull(key, "key cannot be null");
        Map<String, String> oldMap = copyOnInheritThreadLocal.get();
        Integer lastOperation = getAndSetLastOperation(WRITE_OPERATION);
        BranchUtils.isTureOrFalse(oldMap == null || lastOperationIsReadOrNull(lastOperation))
                .trueOrFalseHandle(() -> {
                    Map<String, String> newMap = duplicateAndInsertNewMap(oldMap);
                    newMap.put(key, val);
                }, () -> oldMap.put(key, val));
    }

    @Override
    public String get(String key) {
        Assert.notNull(key, "key cannot be null");
        Map<String, String> map = copyOnInheritThreadLocal.get();
        if (map != null) {
            return map.get(key);
        }
        return Standard.Str.EMPTY;
    }

    @Override
    public void remove(String key) {
        Assert.notNull(key, "key cannot be null");
        Map<String, String> oldMap = copyOnInheritThreadLocal.get();
        BranchUtils.nonNull(oldMap).presentHandle(om -> {
            Integer lastOperation = getAndSetLastOperation(WRITE_OPERATION);
            BranchUtils.isTureOrFalse(lastOperationIsReadOrNull(lastOperation)).trueOrFalseHandle(
                    () -> {
                        Map<String, String> newMap = duplicateAndInsertNewMap(oldMap);
                        newMap.remove(key);
                    }, () -> oldMap.remove(key)
            );
        });
    }

    @Override
    public void clear() {
        LAST_OP_CONTEXT.set(WRITE_OPERATION);
        copyOnInheritThreadLocal.remove();
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return copyOnInheritThreadLocal.get();
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        LAST_OP_CONTEXT.set(WRITE_OPERATION);
        Map<String, String> newMap = Collections.synchronizedMap(new HashMap<>());
        newMap.putAll(contextMap);
        copyOnInheritThreadLocal.set(newMap);
    }

    @Override
    public void pushByKey(String key, String value) {
        threadLocalMapOfDeques.pushByKey(key, value);
    }

    @Override
    public String popByKey(String key) {
        return threadLocalMapOfDeques.popByKey(key);
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        return threadLocalMapOfDeques.getCopyOfDequeByKey(key);
    }

    @Override
    public void clearDequeByKey(String key) {
        threadLocalMapOfDeques.clearDequeByKey(key);
    }

    @SuppressWarnings("all")
    private Integer getAndSetLastOperation(int operation) {
        Integer lastOperation = LAST_OP_CONTEXT.get();
        LAST_OP_CONTEXT.set(operation);
        return lastOperation;
    }

    private static boolean lastOperationIsReadOrNull(Integer lastOperation) {
        return lastOperation == null || lastOperation == READ_OPERATION;
    }

    @SuppressWarnings("all")
    private Map<String, String> duplicateAndInsertNewMap(Map<String, String> oldMap) {
        Map<String, String> newMap = Collections.synchronizedMap(new HashMap<>());
        if (oldMap != null) {
            // 避免在写 newMap 时，父线程同时在更新 oldMap
            synchronized (oldMap) {
                newMap.putAll(oldMap);
            }
        }
        copyOnInheritThreadLocal.set(newMap);
        return newMap;
    }
}
