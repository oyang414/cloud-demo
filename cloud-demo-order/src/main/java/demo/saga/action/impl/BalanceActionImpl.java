
package demo.saga.action.impl;

import demo.saga.action.BalanceAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description 余额Action实现
 * @Date 0:48 2022/4/14
 */
@Service("balanceAction")
@Slf4j
public class BalanceActionImpl implements BalanceAction {


    /**
     * @Author ouyangxingjie
     * @Description 扣减余额
     * @Date 0:53 2022/4/14
     * @param businessKey 业务key
     * @param amount 金额
     * @param params 参数
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params) {
        //根据状态机设计的业务，此处属于扣减余额的事务，属于第二阶段，如果余额扣减失败，则会调用compensateReduce进行补偿操作
        if(params != null && "true".equals(params.get("throwException"))){
            throw new RuntimeException("reduce balance failed");
        }
        if ((int)(Math.random() * 100) % 2 == 0) {
            throw new RuntimeException("模拟随机异常，扣减账户余额失败");
        }
        log.info("reduce balance succeed, amount: " + amount + ", businessKey:" + businessKey);
        return true;
    }

    /**
     * @Author ouyangxingjie
     * @Description 补偿扣减余额方法
     * @Date 0:53 2022/4/14
     * @param businessKey 业务key
     * @param params 参数
     * @return boolean
     */
    @Override
    public boolean compensateReduce(String businessKey, Map<String, Object> params) {
        //如果当前阶段（第二阶段）余额扣减失败（也就是说整个saga事务为FAIL状态），则在该处行补偿操作，并且也要将第一阶段进行补偿操作
        if (params != null && "true".equals(params.get("throwException"))) {
            throw new RuntimeException("compensate reduce balance failed");
        }
        log.info("compensate reduce balance succeed, businessKey:" + businessKey);
        return true;
    }
}