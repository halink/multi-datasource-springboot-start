package com.halink.scaffold.config.multidatasource;

import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * 数据源配置
 *
 * @author halink
 * @date 2021-01-01 17:18
 */
public interface InterfaceDataSourceConfig {
    /**
     * 创建xa数据源
     *
     * @return xa数据源
     */
    XADataSource dataSource();

    /**
     * 包装AtomikosDataSource
     *
     * @return AtomikosDataSource
     */
    DataSource xaDataSource(XADataSource xaDataSource);

    /**
     * 创建 sqlSessionFactory
     *
     * @return sqlSessionFactory
     */
    SqlSessionFactory sqlSessionFactory(DataSource atomikosDataSource) throws Exception;
}
