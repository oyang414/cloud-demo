package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 15:31 2022/2/28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    //使用 Sentinel 保护RestTemplate服务调用
    // TODO：狗日的，好像版本springBoot、springCloud版本不兼容，@SentinelRestTemplate没生效
    //@LoadBalanced
    //@SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class,
    //        fallback="fallback",fallbackClass = ExceptionUtil.class)
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }


/*    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cloud-demo-consumer-route", r -> r.path("/test_consumer/**")
                        .uri("http://localhost:8070"))
                .build();
    }*/
}
