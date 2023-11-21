package cn.com.mockingbird.robin.common.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * ClassUtils 工具测试
 *
 * @author zhaopeng
 * @date 2023/11/21 16:48
 **/
@SuppressWarnings("all")
public class ClassUtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("-------------- 测试 Start ---------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------- 测试 End ---------------");
    }

    @Test
    void test() {
        test(new SubResponse());
        test(new Response<Person>() {
            @Override
            public void onSuccess(Person data) {
                data.name = "张三";
            }
        });
        test(new Response<List<Person>>() {
            @Override
            public void onSuccess(List<Person> data) {

            }
        });
    }

    public interface Response<T> {
        void onSuccess(T data);
    }

    public static class BaseResponse<T> {

    }

    public static class SubResponse extends BaseResponse<Person> {

    }

    public static class Person {
        public String name;
    }

    public static void test(SubResponse subResponse) {
        Class<?> classT = ClassUtils.getClassT(subResponse, 0);
        System.out.println(classT.getSimpleName());
    }

    public static void test(Response<?> response) {
        Class<?> classT = ClassUtils.getInterfaceT(response, 0);
        System.out.println(classT.getSimpleName());
    }
}
