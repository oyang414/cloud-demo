package demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @description: loadbalancer请求客户端配置
 * @author: ouyangxingjie
 * @create: 2022/3/8 18:49
 **/
@Configuration
@LoadBalancerClient(name = "cloud-demo-provider", configuration = MyLoadBalancerConfiguration.class)
public class WebClientConfig {

    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
