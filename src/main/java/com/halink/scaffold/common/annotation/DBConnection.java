package com.halink.scaffold.common.annotation;


import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;

import java.lang.annotation.*;

/**
 * 在mapper上指定库
 *
 * @author halink
 * @date 2020-12-29 16:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBConnection {
    /**
     * 指定库,默认master
     */
    DataSourceTypeEnum value() default DataSourceTypeEnum.MASTER;
}
