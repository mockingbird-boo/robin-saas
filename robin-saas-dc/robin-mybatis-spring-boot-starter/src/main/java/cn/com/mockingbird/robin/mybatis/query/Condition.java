package cn.com.mockingbird.robin.mybatis.query;

import java.lang.annotation.*;

/**
 * 查询条件主键
 * <p>
 * 封装了 EQ、NE、LIKE、GT、LT、GE、LE、IN、BETWEEN 等条件注解
 *
 * @author zhaopeng
 * @date 2023/10/12 11:08
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Condition {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface EQ {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface NE {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface LIKE {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface GT {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface LT {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface GE {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface LE {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface IN {
        Logic logic() default Logic.AND;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface BETWEEN {
        Logic logic() default Logic.AND;
    }

}
