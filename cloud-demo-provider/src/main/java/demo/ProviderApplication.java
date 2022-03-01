package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 15:31 2022/2/28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ProviderApplication.class, args);
       /* while (true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            System.err.println("user name :" + userName + "; age: " + userAge);
            String testContent = applicationContext.getEnvironment().getProperty("test.content");
            System.err.println("test.content :" + testContent);
            String baseContent = applicationContext.getEnvironment().getProperty("base.content");
            System.err.println("base.content :" + baseContent);
            TimeUnit.SECONDS.sleep(1);
        }*/
    }

}
