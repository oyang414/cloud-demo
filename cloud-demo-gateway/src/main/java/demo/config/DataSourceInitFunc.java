package demo.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.RuleType;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.DynamicSentinelProperty;
import com.alibaba.csp.sentinel.property.SentinelProperty;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            //加载 gateway flow 限流规则
            if(RuleType.GW_FLOW.equals(nacos.getRuleType())) {
                System.err.println("开始加载...");
                ReadableDataSource<String, Set<GatewayFlowRule>> gatewayDataSource = new NacosDataSource<>(
                        nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(),
                        source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {
                        }));
                GatewayRuleManager.register2Property(gatewayDataSource.getProperty());
            }
        });
        return new DataSourceInitFunc();
    }
}
