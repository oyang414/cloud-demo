package demo.controller;

import demo.service.OrderTccService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description 订单Controller（TCC模式）
 * @Date 14:40 2022/4/10
 */
@RestController
@RequestMapping("/orderTcc")
public class OrderTccController {
    @Autowired
    private OrderTccService orderTccService;

    @PostMapping("/tcc")
    @GlobalTransactional
    public String insert() {
        Map<String, String> params = new HashMap<>();
        params.put("code","123");
        return orderTccService.insert(params);
    }
}
