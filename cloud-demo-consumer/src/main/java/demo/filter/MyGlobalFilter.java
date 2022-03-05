package demo.filter;/**
 * @Author ouyangxingjie
 * @Description
 * @Date 12:19 2022/3/5
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: gateway自定义全局过滤器
 * @author: ouyangxingjie
 * @create: 2022/3/5 12:19
 **/
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        log.info("MyGlobalFilter前置逻辑");
        return chain.filter(exchange).then(Mono.fromRunnable(() ->
        {
            log.info("MyGlobalFilter后置逻辑");
        }));
    }

    @Override
    public int getOrder()
    {
        return 1;
    }
}
