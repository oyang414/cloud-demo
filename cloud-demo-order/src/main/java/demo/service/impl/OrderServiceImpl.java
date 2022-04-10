package demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.client.StorageClient;
import demo.dto.OrderDTO;
import demo.entity.Order;
import demo.mapper.OrderMapper;
import demo.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:15 2022/3/29
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StorageClient storageClient;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @GlobalTransactional
    public Map<String, Object> purchase(OrderDTO orderDTO) {
        Map<String,Object> resultMap = new HashMap<>();
        Order order = new Order();
        order.setAddress(orderDTO.getAddress());
        order.setCreateTime(LocalDateTime.now());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderNum(orderDTO.getOrderNum());
        // 本地事务
        orderMapper.insert(order);
        log.info(">>>> insert result = {} <<<<",order);

        // 分支事务
        Map<String,Object> response = storageClient.reduce(orderDTO.getCode(),orderDTO.getNums());
        log.info(">>>> storage response={}",response);
        resultMap.put("success",true);
        return resultMap;
    }
}
