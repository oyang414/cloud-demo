package demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 15:55 2022/2/28
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/echo")
    @SentinelResource(value = "consumer_hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String echo(@RequestParam("name") String name) throws Exception{
        //int a = 1/0;
        Thread.sleep(1000);
        ServiceInstance serviceInstance = loadBalancerClient.choose("cloud-demo-provider");
        String path = String.format("http://%s:%s/provider/echo?name=%s",serviceInstance.getHost(),serviceInstance.getPort(),name);
        System.out.println("request path:" + path);
        return restTemplate.getForObject(path,String.class);
    }
    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(String name) {
        return String.format("consumer: 服务异常回调 %s", name);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String name, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "consumer: 违反sentinel配置的规则，进行服务降级 " + name;
    }
}
