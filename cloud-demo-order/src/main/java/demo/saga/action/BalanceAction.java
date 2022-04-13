
package demo.saga.action;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description 余额Action接口
 * @Date 0:54 2022/4/14
 */
public interface BalanceAction {

    /**
     * @Author ouyangxingjie
     * @Description 扣减余额
     * @Date 0:53 2022/4/14
     * @param businessKey 业务key
     * @param amount 金额
     * @param params 参数
     * @return boolean
     */
    boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params);

    /**
     * @Author ouyangxingjie
     * @Description 补偿扣减余额方法
     * @Date 0:53 2022/4/14
     * @param businessKey 业务key
     * @param params 参数
     * @return boolean
     */
    boolean compensateReduce(String businessKey, Map<String, Object> params);

}
