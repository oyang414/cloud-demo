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
    public String echo(@RequestParam("name") String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("cloud-demo-provider");
        String path = String.format("http://%s:%s/provider/echo?name=%s",serviceInstance.getHost(),serviceInstance.getPort(),name);
        System.out.println("request path:" + path);
        return restTemplate.getForObject(path,String.class);
    }

}
