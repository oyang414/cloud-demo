package demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @description: gateway自定义局部过滤器
 * @author: ouyangxingjie
 * @create: 2022/3/5 12:03
 **/
@Slf4j
public class MyGateWayFilter implements GatewayFilter, Ordered
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        log.info("进入MyGateWayFilter...");
        URI uri = exchange.getRequest().getURI();
        log.info("host:{}",uri.getHost());
        log.info("port:{}",uri.getPort());
        log.info("path:{}",uri.getPath());
        String url = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info("请求URL:" + url);
        log.info("method:" + exchange.getRequest().getMethod());
        log.info("MyGateWayFilter前置逻辑");
        return chain.filter(exchange).then(Mono.fromRunnable(() ->
        {
            log.info("MyGateWayFilter后置逻辑");
        }));
    }

    //值越小，优先级越高
    //int HIGHEST_PRECEDENCE = -2147483648;
    //int LOWEST_PRECEDENCE = 2147483647;
    @Override
    public int getOrder()
    {
        //return HIGHEST_PRECEDENCE;
        return 1;
    }
}