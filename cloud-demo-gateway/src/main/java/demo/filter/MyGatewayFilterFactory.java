package demo.filter;/**
 * @Author ouyangxingjie
 * @Description
 * @Date 12:12 2022/3/5
 */

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @description: gateway自定义局部过滤器工厂方法
 * @author: ouyangxingjie
 * @create: 2022/3/5 12:12
 **/
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<Object>
{
    @Override
    public GatewayFilter apply(Object config)
    {
        return new MyGateWayFilter();
    }
}
