package com.halink.scaffold.config.multidatasource;

import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源
 *
 * @author halink
 * @date 2020-12-29 16:09
 */
@Configuration
public class MultiDatasourceSqlSessionConfig {

    @Resource
    @Qualifier("masterSqlSessionFactory")
    private SqlSessionFactory masterSqlSessionFactory;
    @Resource
    @Qualifier("slaveSqlSessionFactory")
    private SqlSessionFactory slaveSqlSessionFactory;

    @Bean(name = "sqlSessionTemplate")
    public MultiDatasourceSqlSessionTemplate sqlSessionTemplate() {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>(2);
        sqlSessionFactoryMap.put(DataSourceTypeEnum.MASTER, masterSqlSessionFactory);
        sqlSessionFactoryMap.put(DataSourceTypeEnum.SLAVE, slaveSqlSessionFactory);
        MultiDatasourceSqlSessionTemplate customSqlSessionTemplate = new MultiDatasourceSqlSessionTemplate(masterSqlSessionFactory);
        customSqlSessionTemplate.setTargetSqlSessionFactories(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }

}
