package demo.controller;

import demo.dto.OrderDTO;
import demo.service.OrderService;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:11 2022/3/29
 */
@RestController
@RequestMapping("/orderSaga")
@Slf4j
public class OrderSagaController {


    @Autowired
    private StateMachineEngine stateMachineEngine;


    @GetMapping("/create")
    public String create() {
        log.info("=========开始创建订单============");
        Map<String, Object> startParams = new HashMap<>(3);
        //唯一健
        String businessKey = String.valueOf(System.currentTimeMillis());
        startParams.put("businessKey", businessKey);
        startParams.put("count", 10);
        startParams.put("amount", new BigDecimal("400"));

        //同步执行
        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("reduceInventoryAndBalance", null, businessKey, startParams);

        if(ExecutionStatus.SU.equals(inst.getStatus())){
            log.info("创建订单成功,saga transaction execute Succeed. XID: " + inst.getId());
            return "创建订单成功";
        }else{
            log.info("创建订单失败 ,saga transaction execute failed. XID: " + inst.getId());
            return "创建订单失败";
        }
    }
}