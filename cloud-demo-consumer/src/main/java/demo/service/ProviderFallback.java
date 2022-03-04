package demo.service;

import org.springframework.stereotype.Component;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 10:21 2022/3/4
 */
@Component
public class ProviderFallback implements ProviderClient{

    @Override
    public String echo(String name) {
        return "provider feign :服务降级...";
    }
}
