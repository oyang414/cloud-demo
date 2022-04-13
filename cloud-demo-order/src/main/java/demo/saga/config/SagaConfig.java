package demo.saga.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 22:13 2022/4/13
 */
@Configuration
public class SagaConfig {


    @Bean
    public ThreadPoolExecutorFactoryBean threadExecutor() {
        ThreadPoolExecutorFactoryBean threadExecutor = new ThreadPoolExecutorFactoryBean();
        threadExecutor.setThreadNamePrefix("SAGA_ASYNC_EXE_");
        threadExecutor.setCorePoolSize(1);
        threadExecutor.setMaxPoolSize(20);
        return threadExecutor;
    }

    @Bean
    public DbStateMachineConfig dbStateMachineConfig(ThreadPoolExecutorFactoryBean threadExecutor, DataSource hikariDataSource) throws IOException {
        DbStateMachineConfig dbStateMachineConfig = new DbStateMachineConfig();
        dbStateMachineConfig.setDataSource(hikariDataSource);
        dbStateMachineConfig.setThreadPoolExecutor((ThreadPoolExecutor) threadExecutor.getObject());
        /**
         *这里配置了json文件的路径，TM在初始化的时候，会把json文件解析成StateMachineImpl类，如果数据库没有保存这个状态机，则存入数据库seata_state_machine_def表，
         *如果数据库有记录，则取最新的一条记录，并且注册到StateMachineRepositoryImpl，
         *注册的Map有2个，一个是stateMachineMapByNameAndTenant，key格式是(stateMachineName + "_" + tenantId),
         *一个是stateMachineMapById，key是stateMachine.getId()
         *具体代码见StateMachineRepositoryImpl类registryStateMachine方法
         *这个注册的触发方法在DefaultStateMachineConfig的初始化方法init()，这个类是DbStateMachineConfig的父类
         */
        dbStateMachineConfig.setResources(new PathMatchingResourcePatternResolver().getResources("classpath*:statelang/reduce_inventory_and_balance.json"));//json文件
        dbStateMachineConfig.setEnableAsync(true);
        dbStateMachineConfig.setApplicationId("cloud-demo-order");
        dbStateMachineConfig.setTxServiceGroup("order-service-group");
        return dbStateMachineConfig;
    }

    @Bean
    public ProcessCtrlStateMachineEngine stateMachineEngine(DbStateMachineConfig dbStateMachineConfig) {
        ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
        stateMachineEngine.setStateMachineConfig(dbStateMachineConfig);
        return stateMachineEngine;
    }

    @Bean
    public StateMachineEngineHolder stateMachineEngineHolder(ProcessCtrlStateMachineEngine stateMachineEngine) {
        StateMachineEngineHolder stateMachineEngineHolder = new StateMachineEngineHolder();
        stateMachineEngineHolder.setStateMachineEngine(stateMachineEngine);
        return stateMachineEngineHolder;
    }


}
