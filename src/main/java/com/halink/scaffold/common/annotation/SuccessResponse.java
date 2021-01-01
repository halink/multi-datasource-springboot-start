package com.halink.scaffold.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author halink
 * @date 2020/9/28 10:32 上午
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessResponse {
}
