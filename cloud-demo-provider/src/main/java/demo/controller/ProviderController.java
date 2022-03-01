package demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

/**
 * @Author ouyangxingjie
 * @Description 服务提供者
 * @Date 15:29 2022/2/28
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @RequestMapping("/echo")
    @SentinelResource(value = "provider_hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String echo(String name) throws Exception{
        //int a = 1/0;
        System.out.println("生产者收到消息："+name);
        return "hello,"+ name;
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(String name) {
        return String.format("provider: 服务降级 %s", name);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String name, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "provider: 服务熔断 " + name;
    }

}
