
package demo.saga.action;

/**
 * @Author ouyangxingjie
 * @Description 库存Action接口
 * @Date 0:54 2022/4/14
 */
public interface InventoryAction {

    /**
     * @Author ouyangxingjie
     * @Description 扣减库存
     * @Date 0:54 2022/4/14
     * @param businessKey 业务key
     * @param count 库存数量
     * @return boolean
     */
    boolean reduce(String businessKey, int count);

    /**
     * @Author ouyangxingjie
     * @Description 扣减库存补偿方法
     * @Date 0:55 2022/4/14
     * @param businessKey 业务key
     * @return boolean
     */
    boolean compensateReduce(String businessKey);
}
