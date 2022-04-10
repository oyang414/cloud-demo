package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.entity.Order;
import org.springframework.stereotype.Component;

@Component("orderMapper")
public interface OrderMapper extends BaseMapper<Order> {
}