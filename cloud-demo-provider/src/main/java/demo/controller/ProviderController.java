package demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.Thread.sleep;

/**
 * @Author ouyangxingjie
 * @Description 服务提供者
 * @Date 15:29 2022/2/28
 */
@RestController
@RequestMapping("/provider")
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class ProviderController {

    @Value("${user.name}")
    private String userName;
    @Value("${user.age}")
    private String userAge;

    /**
     * @Author ouyangxingjie
     * @Description echo方法
     * @Date 17:14 2022/3/3
     * @param name 名称
     * @return java.lang.String
     */
    //fallback：失败调用，若本接口出现未知异常，则调用fallback指定的接口。
    //blockHandler：sentinel定义的失败调用或限制调用，若本次访问被限流或服务降级，则调用blockHandler指定的接口。
    @RequestMapping("/echo")
    @SentinelResource(value = "provider_hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String echo(String name) throws Exception{
        //int a = 1/0;
        System.out.println("生产者收到消息："+name);
        return "hello,"+ name;
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(String name) {
        return String.format("provider: 服务异常回调 %s", name);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String name, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "provider: 违反sentinel配置的规则，进行服务降级 " + name;
    }

    /**
     * @Author ouyangxingjie
     * @Description 查询 通过@RefreshScope注解动态获取nacos配置中心的用户信息
     * @Date 17:16 2022/3/3
     * @return java.lang.String
     */
    //动态获取nacos配置中心的 user.name ,user.age
    @RequestMapping("/user")
    public String getUser(){
        return "user: [name:" + userName + "," + "age:" + userAge +"]";
    }

}
