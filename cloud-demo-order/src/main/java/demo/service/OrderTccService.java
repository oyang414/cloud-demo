package demo.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description orderTCC模式接口
 * @Date 14:29 2022/4/10
 */
/**
 * @LocalTCC:一定需要注解在接口上，否则不生效，此接口可以是寻常的业务接口，只要实现了TCC的两阶段提交对应方法便可，适用于SpringCloud+Feign模式下的TCC。
 * @TwoPhaseBusinessAction:注解try方法，其中name为当前tcc方法的bean名称，写方法名便可（全局唯一），commitMethod指向提交方法，rollbackMethod指向事务回滚方法。指定好三个方法之后，seata会根据全局事务的成功或失败，自动调用提交方法或者回滚方法。
 * @BusinessActionContextParameter:使用该注解可以将参数传递到二阶段commit或者rollback的方法中，方便调用。
 * BusinessActionContext:TCC事务上下文，使用BusinessActionContext.getActionContext("params")便可以得到一阶段try中定义的参数，在二阶段参考此参数进行业务回滚操作。
 * 建议:可以在try方法中使用@Transational，直接通过spring来控制关系型数据库的事务，进行回滚的操作，而非关系型数据库等中间件的回滚操作可以交给rollbackMethod方法处理。
 * 建议:try接口不可以捕获异常，否则TCC将识别该操作为成功，直接执行二阶段commit方法。
 */
@LocalTCC
public interface OrderTccService {

    @TwoPhaseBusinessAction(name = "insert", commitMethod = "commitTcc", rollbackMethod = "cancel")
    String insert(
            @BusinessActionContextParameter(paramName = "params") Map<String, String> params
    );

    /**
     *
     * 二阶段提交方法
     *
     * @param context 上下文
     * @return boolean
     */
    boolean commitTcc(BusinessActionContext context);

    /**
     * 二阶段取消方法
     *
     * @param context 上下文
     * @return boolean
     */
    boolean cancel(BusinessActionContext context);
}

