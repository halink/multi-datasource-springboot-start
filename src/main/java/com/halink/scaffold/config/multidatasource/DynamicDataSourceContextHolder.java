package com.halink.scaffold.config.multidatasource;


import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;

/**
 * 动态数据源上下文
 *
 * @author halink
 * @date 2020-12-29 16:20
 */
public class DynamicDataSourceContextHolder {
    /**
     * 动态数据源名称上下文
     **/
    private static final ThreadLocal<DataSourceTypeEnum> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    private DynamicDataSourceContextHolder() {
    }

    /**
     * 获取数据源名称
     **/
    public static DataSourceTypeEnum getContextKey() {
        DataSourceTypeEnum key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? DataSourceTypeEnum.MASTER : key;
    }

    /**
     * 设置/切换数据源
     **/
    public static void setContextKey(DataSourceTypeEnum key) {
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /**
     * 删除当前数据源名称
     **/
    public static void removeContextKey() {
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}
