package demo.config;/**
 * @Author ouyangxingjie
 * @Description
 * @Date 12:18 2022/3/5
 */

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import demo.filter.MyGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @description: gateway全局过滤器配置
 * @author: ouyangxingjie
 * @create: 2022/3/5 12:18
 **/
@Configuration
public class GlobalFilterConfig {
    /**
     * @Author ouyangxingjie
     * @Description 自定义全局过滤器
     * @Date 12:39 2022/3/5
     * @return org.springframework.cloud.gateway.filter.GlobalFilter
     */
    @Bean
    @Order(-1)
    public GlobalFilter myGlobalFilter()
    {
        return new MyGlobalFilter();
    }



}
