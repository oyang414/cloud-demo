package demo.service.impl;


import demo.service.StorageTccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description storageTCC模式接口实现类
 * @Date 14:31 2022/4/10
 */
@Service
public class StorageTccServiceImpl implements StorageTccService {



    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String insert(Map<String, String> params){
        System.out.println("==========storage==========");
        System.out.println("try------------------> xid = " + RootContext.getXID());
        //throw new RuntimeException("服务tcc测试回滚");
        return "success";
    }

    @Override
    public boolean commitTcc(BusinessActionContext context) {
        System.out.println("==========storage==========");
        System.out.println("commit------------------> xid =" + context.getXid());
        //如果这里commit的时候抛出异常，那么会不断进行重试（有个定时器默认每隔1秒重试提交事务）
        //throw new RuntimeException("服务tcc测试回滚");
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext context) {
        System.out.println("==========storage==========");
        System.out.println("cancel------------------> xid =" + context.getXid());
        return true;
    }
}
