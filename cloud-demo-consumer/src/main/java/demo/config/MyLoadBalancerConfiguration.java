package demo.config;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


/**
 * @description: loadbalancer负载均衡配置
 * @author: ouyangxingjie
 * @create: 2022/3/8 19:44
 **/
public class MyLoadBalancerConfiguration {
    /**
     * @Author ouyangxingjie
     * @Description 轮询算法，此处可以配置自己的负载均衡算法
     * @Date 19:56 2022/3/8
     * @param environment
     * @param loadBalancerClientFactory
     * @return org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer<org.springframework.cloud.client.ServiceInstance>
     */
    @Bean
    ReactorLoadBalancer<ServiceInstance> roundRobinLoadBalancer(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RoundRobinLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),
                name);
    }
}
