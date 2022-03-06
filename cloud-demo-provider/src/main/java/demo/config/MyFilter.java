package demo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;

/**
 * @Author ouyangxingjie
 * @Description MyFilter
 * @Date 14:04 2021/8/17
 */
@Configuration
@Slf4j
public class MyFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        URI uri = exchange.getRequest().getURI();
        log.info("host:{}",uri.getHost());
        log.info("port:{}",uri.getPort());
        log.info("path:{}",uri.getPath());
        String url = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info("请求URL:" + url);
        log.info("method:" + exchange.getRequest().getMethod());
        //后处理，打印完成信号的值
        return chain.filter(exchange).doFinally(signal ->  System.out.println("signal="+signal.toString()));

        /* 其他几种
         * or 1: 建议尽量采用链式的fluent连贯写法
         *   Mono<Void>  completeMono = chain.filter(exchange);
         *   return completeMono.doFinally(signal -> System.out.println("signal="+signal.toString()));
         */
        //or 2: return chain.filter(exchange).thenEmpty(other);
        //or 3: return chain.filter(exchange).thenMany(other).map(..)....then();
    }
}
