package demo.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.RuleType;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author ouyangxingjie
 * @Description 从nacos初始化sentinel配置数据
 * @Date 11:04 2022/3/2
 */
@Configuration
public class DataSourceInitFunc {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public DataSourceInitFunc init() throws Exception {
        System.err.println("从nacos初始化sentinel配置数据。。。");
        sentinelProperties.getDatasource().entrySet().stream().filter(map -> {
            return map.getValue().getNacos() != null;
        }).forEach(map -> {
            NacosDataSourceProperties nacos = map.getValue().getNacos();
            //加载 flow 限流规则
            if(RuleType.FLOW.equals(nacos.getRuleType())) {
                ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(
                        nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(),
                        source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                        }));
                FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
            }
            //加载 degrade 熔断规则
            if(RuleType.DEGRADE.equals(nacos.getRuleType())){
                ReadableDataSource<String, List<DegradeRule>> flowRuleDataSource = new NacosDataSource<>(
                        nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(),
                        source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                        }));
                DegradeRuleManager.register2Property(flowRuleDataSource.getProperty());
            }
            // TODO:热点和系统规则和上述两个规则类似，懒得写了
        });
        return new DataSourceInitFunc();
    }
}
