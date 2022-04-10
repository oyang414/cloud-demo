package demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.dto.OrderDTO;
import demo.entity.Order;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:12 2022/3/29
 */
public interface OrderService {
    Map<String,Object>  purchase(OrderDTO dto);
}
