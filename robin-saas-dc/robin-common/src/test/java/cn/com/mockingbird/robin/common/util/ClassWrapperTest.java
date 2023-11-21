package cn.com.mockingbird.robin.common.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

/**
 * ClassWrapper 测试类
 *
 * @author zhaopeng
 * @date 2023/11/21 17:24
 **/
public class ClassWrapperTest {

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
        // {} 相当于匿名内部类实例化对象，继承了 ClassWrapper
        ClassWrapper<Person<Student>> classWrapper = new ClassWrapper<>(){};
        Type type = classWrapper.getType();
        Type parameterizedType = classWrapper.getParameterizedType();
        Class<?> classT = (Class<?>) type;
        System.out.println("parameterizedType:" + parameterizedType.getTypeName());
        System.out.println("泛型T name = " + type.getTypeName());
        System.out.println("T = " + classT.getSimpleName());
        // Gson 好像可以转
//        String json = "{\"data\":{\"name\":\"小明\", \"age\": 18}}";
//        Person<Student> person = JSONObject.parseObject(json, parameterizedType);
//        System.out.println(person);
    }

    static class Person<T> {
        T data;

        @Override
        public String toString() {
            return "Person{" +
                    "data=" + data +
                    '}';
        }
    }

    static class Student {
        String name;
        int age;

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
