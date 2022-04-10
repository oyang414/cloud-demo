package demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 15:31 2022/2/28
 */

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "demo.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
