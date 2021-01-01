package com.halink.scaffold.config.multidatasource;

import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.*;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.mybatis.spring.SqlSessionUtils.*;

@Slf4j
public class MultiDatasourceSqlSessionTemplate extends SqlSessionTemplate {

    private final SqlSessionFactory sqlSessionFactory;
    private final ExecutorType executorType;
    private final SqlSession sqlSessionProxy;
    private final PersistenceExceptionTranslator exceptionTranslator;
    private final SqlSessionFactory defaultTargetSqlSessionFactory;
    private Map<Object, SqlSessionFactory> targetSqlSessionFactories;

    public MultiDatasourceSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
    }

    public MultiDatasourceSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
        this(sqlSessionFactory, executorType, new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true));
    }

    public MultiDatasourceSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator) {

        super(sqlSessionFactory, executorType, exceptionTranslator);

        this.sqlSessionFactory = sqlSessionFactory;
        this.executorType = executorType;
        this.exceptionTranslator = exceptionTranslator;

        this.sqlSessionProxy = (SqlSession) newProxyInstance(SqlSessionFactory.class.getClassLoader(), new Class[]{SqlSession.class}, new SqlSessionInterceptor());

        this.defaultTargetSqlSessionFactory = sqlSessionFactory;
    }

    public void setTargetSqlSessionFactories(Map<Object, SqlSessionFactory> targetSqlSessionFactories) {
        this.targetSqlSessionFactories = targetSqlSessionFactories;
    }


    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        DataSourceTypeEnum contextKey = DynamicDataSourceContextHolder.getContextKey();
        log.info("sqlSessionFactory type:{}", contextKey);
        SqlSessionFactory targetSqlSessionFactory = targetSqlSessionFactories.get(contextKey);
        if (targetSqlSessionFactory != null) {
            return targetSqlSessionFactory;
        } else if (defaultTargetSqlSessionFactory != null) {
            return defaultTargetSqlSessionFactory;
        } else {
            Assert.notNull(targetSqlSessionFactories, "Property 'targetSqlSessionFactorys' or 'defaultTargetSqlSessionFactory' are required");
        }
        return this.sqlSessionFactory;
    }

    @Override
    public Configuration getConfiguration() {
        return this.getSqlSessionFactory().getConfiguration();
    }

    @Override
    public ExecutorType getExecutorType() {
        return this.executorType;
    }

    @Override
    public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
        return this.exceptionTranslator;
    }


    @Override
    public <T> T selectOne(String statement) {
        return this.sqlSessionProxy.selectOne(statement);
    }


    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return this.sqlSessionProxy.selectOne(statement, parameter);
    }


    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return this.sqlSessionProxy.selectMap(statement, mapKey);
    }


    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return this.sqlSessionProxy.selectMap(statement, parameter, mapKey);
    }


    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
    }


    @Override
    public <E> List<E> selectList(String statement) {
        return this.sqlSessionProxy.selectList(statement);
    }


    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return this.sqlSessionProxy.selectList(statement, parameter);
    }


    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectList(statement, parameter, rowBounds);
    }


    @Override
    public void select(String statement, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, handler);
    }


    @Override
    public void select(String statement, Object parameter, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, handler);
    }


    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
    }


    @Override
    public int insert(String statement) {
        return this.sqlSessionProxy.insert(statement);
    }


    @Override
    public int insert(String statement, Object parameter) {
        return this.sqlSessionProxy.insert(statement, parameter);
    }


    @Override
    public int update(String statement) {
        return this.sqlSessionProxy.update(statement);
    }


    @Override
    public int update(String statement, Object parameter) {
        return this.sqlSessionProxy.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return this.sqlSessionProxy.delete(statement);
    }


    @Override
    public int delete(String statement, Object parameter) {
        return this.sqlSessionProxy.delete(statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }


    @Override
    public void commit() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }


    @Override
    public void commit(boolean force) {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }


    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    @Override
    public void rollback(boolean force) {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Manual close is not allowed over a Spring managed SqlSession");
    }


    @Override
    public void clearCache() {
        this.sqlSessionProxy.clearCache();
    }


    @Override
    public Connection getConnection() {
        return this.sqlSessionProxy.getConnection();
    }

    @Override
    public List<BatchResult> flushStatements() {
        return this.sqlSessionProxy.flushStatements();
    }


    private class SqlSessionInterceptor implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final SqlSession sqlSession = getSqlSession(MultiDatasourceSqlSessionTemplate.this.getSqlSessionFactory(), MultiDatasourceSqlSessionTemplate.this.executorType, MultiDatasourceSqlSessionTemplate.this.exceptionTranslator);
            try {
                Object result = method.invoke(sqlSession, args);
                if (!isSqlSessionTransactional(sqlSession, MultiDatasourceSqlSessionTemplate.this.getSqlSessionFactory())) {
                    sqlSession.commit(true);
                }
                return result;
            } catch (Exception t) {
                Throwable unwrapped = unwrapThrowable(t);
                if (MultiDatasourceSqlSessionTemplate.this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
                    Throwable translated = MultiDatasourceSqlSessionTemplate.this.exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
                    if (translated != null) {
                        unwrapped = translated;
                    }
                }
                throw unwrapped;
            } finally {
                closeSqlSession(sqlSession, MultiDatasourceSqlSessionTemplate.this.getSqlSessionFactory());
            }
        }
    }

}
