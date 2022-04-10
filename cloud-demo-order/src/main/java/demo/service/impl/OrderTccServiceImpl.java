package demo.service.impl;

import demo.client.StorageClient;
import demo.service.OrderTccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description orderTCC模式接口实现类
 * @Date 14:31 2022/4/10
 */
@Service
public class OrderTccServiceImpl implements OrderTccService {

    @Autowired
    private StorageClient storageTccClient;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String insert(Map<String, String> params) {
        System.out.println("==========order==========");
        System.out.println("try------------------> xid = " + RootContext.getXID());
        //throw new RuntimeException("服务tcc测试回滚");
        storageTccClient.insert(params.get("code"));
        return "success";
    }

    @Override
    public boolean commitTcc(BusinessActionContext context) {
        System.out.println("==========order==========");
        System.out.println("commit------------------> xid =" + context.getXid());
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext context) {
        System.out.println("==========order==========");
        System.out.println("cancel------------------> xid =" + context.getXid());
        return true;
    }
}
