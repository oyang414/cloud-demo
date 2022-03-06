package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author ouyangxingjie
 * @Description gateway网关
 * @Date 15:31 2022/2/28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(GatewayApplication.class, args);
    }


}
