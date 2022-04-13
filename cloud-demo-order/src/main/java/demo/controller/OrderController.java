package demo.controller;

import demo.dto.OrderDTO;
import demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description 订单Controller（AT模式）
 * @Date 20:11 2022/3/29
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchase(@RequestBody OrderDTO orderDTO) {
        Map<String,Object> resultMap = orderService.purchase(orderDTO);
        return ResponseEntity.ok(resultMap);
    }
}