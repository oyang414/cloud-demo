package demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:48 2022/3/29
 */
@Configuration
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        // 或者使用其他数据源
        return new DruidDataSource();
    }

    /**
     * 将 代理数据源设置为主数据源
     */
    @Primary
    @Bean
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }
}
