package com.halink.scaffold.config.multidatasource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import javax.sql.XADataSource;

@Configuration
public class MasterDataSourceConfig implements InterfaceDataSourceConfig {
    /**
     * 创建xa数据源
     *
     * @return xa数据源
     */
    @Override
    @Bean("masterXADataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public XADataSource dataSource() {
        return new DruidXADataSource();
    }

    /**
     * 包装AtomikosDataSource
     *
     * @return AtomikosDataSource
     */
    @Override
    @Bean("masterAtomikosDataSource")
    public DataSource xaDataSource(@Qualifier("masterXADataSource") XADataSource xaDataSource) {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setXaDataSource(xaDataSource);
        return dataSource;
    }

    /**
     * 创建 sqlSessionFactory
     *
     * @return sqlSessionFactory
     */
    @Override
    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterAtomikosDataSource") DataSource atomikosDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(atomikosDataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/halink/scaffold/modular/mapper/master/*.xml"));
        return bean.getObject();

    }
}
