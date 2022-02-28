package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ouyangxingjie
 * @Description 服务提供者
 * @Date 15:29 2022/2/28
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @RequestMapping("/echo")
    public String echo(String name) {
        System.out.println("生产者收到消息："+name);
        return "hello,"+ name;
    }
}
