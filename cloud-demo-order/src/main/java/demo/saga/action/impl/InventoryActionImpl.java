
package demo.saga.action.impl;


import demo.saga.action.InventoryAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ouyangxingjie
 * @Description 库存Action实现
 * @Date 0:54 2022/4/14
 */
@Service("inventoryAction")
@Slf4j
public class InventoryActionImpl implements InventoryAction {


    /**
     * @Author ouyangxingjie
     * @Description 扣减库存
     * @Date 0:54 2022/4/14
     * @param businessKey 业务key
     * @param count 库存数量
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reduce(String businessKey, int count) {
        //根据状态机设计的业务，此处属于扣减库存的事务，属于第一阶段，如果库存扣减失败，TM则会尝试retry，重试提交，默认5次
        log.info("reduce inventory succeed, count: " + count + ", businessKey:" + businessKey);
        if ((int)(Math.random() * 100) % 2 ==0) {
            throw new RuntimeException("模拟随机异常，扣减库存失败");
        }
        return true;
    }

    /**
     * @Author ouyangxingjie
     * @Description 扣减库存补偿方法
     * @Date 0:55 2022/4/14
     * @param businessKey 业务key
     * @return boolean
     */
    @Override
    public boolean compensateReduce(String businessKey) {
        //如果第二阶段扣减余额的步骤出现异常，则需要调用（第一阶段，也就是当前）此方法，进行第一阶段的补偿操作
        log.info("compensate reduce inventory succeed, businessKey:" + businessKey);
        return true;
    }
}