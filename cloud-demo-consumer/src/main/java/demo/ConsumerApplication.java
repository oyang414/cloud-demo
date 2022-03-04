package demo;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import demo.utils.ExceptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
}
