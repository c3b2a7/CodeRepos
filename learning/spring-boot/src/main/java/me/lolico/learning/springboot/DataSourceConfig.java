package me.lolico.learning.springboot;

import me.lolico.learning.springboot.common.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

/**
 * @author lolico
 */
@Slf4j
@Configuration
public class DataSourceConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /* configuration */

    @Bean
    @Qualifier("defaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dataSource(Map<String, DataSource> dataSourceMap, @Qualifier("defaultDataSource") DataSource defaultDataSource) {
        AbstractRoutingDataSource dataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DynamicDataSourceContextHolder.getKey();
            }

            @Override
            public void setTargetDataSources(Map<Object, Object> targetDataSources) {
                super.setTargetDataSources(targetDataSources);
                DynamicDataSourceContextHolder.setDataSourceMap(targetDataSources);
            }

            @Override
            public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
                if (defaultTargetDataSource instanceof String) {
                    super.setDefaultTargetDataSource(dataSourceMap.get(defaultTargetDataSource));
                    DynamicDataSourceContextHolder.setDefaultKey((String) defaultTargetDataSource);
                } else if (defaultTargetDataSource instanceof DataSource) {
                    super.setDefaultTargetDataSource(defaultTargetDataSource);
                    DynamicDataSourceContextHolder.setDefaultKey(resolveSpecifiedLookupKey((DataSource) defaultTargetDataSource));
                } else {
                    log.info("Why am i here?");
                }
            }

            private String resolveSpecifiedLookupKey(DataSource defaultTargetDataSource) {
                String[] beanDefinitionNames = applicationContext.getBeanNamesForType(defaultTargetDataSource.getClass());
                for (String beanDefinitionName : beanDefinitionNames) {
                    if (applicationContext.getBean(beanDefinitionName) == defaultTargetDataSource) {
                        return beanDefinitionName;
                    }
                }
                return null;
            }
        };
        dataSource.setTargetDataSources(Collections.unmodifiableMap(dataSourceMap));
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        return dataSource;
    }

}
