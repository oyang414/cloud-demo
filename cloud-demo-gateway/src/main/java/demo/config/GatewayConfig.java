package demo.config;/**
 * @Author ouyangxingjie
 * @Description
 * @Date 12:58 2022/3/5
 */

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @description:
 * @author: ouyangxingjie
 * @create: 2022/3/5 12:58
 **/
@Configuration
@Slf4j
public class GatewayConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    // 缺少的bean
    @Bean("sentinel-json-gw-flow-converter")
    public JsonConverter<GatewayFlowRule> jsonGatewayFlowConverter() {
        return new JsonConverter<>(new ObjectMapper(), GatewayFlowRule.class);
    }
    /**
     * @Author ouyangxingjie
     * @Description 配置初始化的限流参数
     * 初始化网关规则
     * GatewayFlowRule：网关限流规则，针对 API Gateway 的场景定制的限流规则，可以针对不同 route 或自定义的 API 分组进行限流，支持针对请求中的参数、Header、来源 IP 等进行定制化的限流。
     * resource：资源名称，可以是网关中的 route 名称或者用户自定义的API 分组名称。
     * resourceMode：规则是针对 API Gateway 的route（RESOURCE_MODE_ROUTE_ID）还是用户在 Sentinel 中定义的API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是route。
     * grade：限流指标维度，同限流规则的grade 字段。
     * count：限流阈值
     * intervalSec：统计时间窗口，单位是秒，默认是1 秒（目前仅对参数限流生效）。
     * controlBehavior：流量整形的控制效果，同限流规则的 controlBehavior 字段，目前支持快速失败和匀速排队两种模式，默认是快速失败。
     * burst：应对突发请求时额外允许的请求数目（目前仅对参数限流生效）。
     * maxQueueingTimeoutMs：匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效。
     * paramItem：参数限流配置。若不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则；否则会转换成热点规则。其中的字段：
     * parseStrategy：从请求中提取参数的策略，目前支持提取来源 IP（PARAM_PARSE_STRATEGY_CLIENT_IP）、Host（PARAM_PARSE_STRATEGY_HOST）、任意 Header（PARAM_PARSE_STRATEGY_HEADER）和任意 URL 参数（PARAM_PARSE_STRATEGY_URL_PARAM）四种模式。
     * fieldName：若提取策略选择 Header 模式或 URL 参数模式，则需要指定对应的 header 名称或 URL 参数名称。
     * pattern 和 matchStrategy：为后续参数匹配特性预留，目前未实现。
     * 用户可以通过 GatewayRuleManager.loadRules(rules)手动加载网关规则，或通过 GatewayRuleManager.register2Property(property)注册动态规则源动态推送（推荐方式）。
     * @Date 14:47 2022/3/6
     * @return void
     */

    @PostConstruct
    public void initGatewayRules() {
      /*  log.info("初始化限流规则...");
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(
                new GatewayFlowRule("provider-route") //资源名称,对应路由id
                        .setCount(1) // 限流阈值
                        .setIntervalSec(1) // 统计时间窗口， 单位是秒， 默认是 1 秒
        );
        GatewayRuleManager.loadRules(rules);*/
    }

    /**
     * @Author ouyangxingjie
     * @Description  自定义限流异常处理
     * @Date 14:47 2022/3/6
     * @return void
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map map = new HashMap();
            map.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
            map.put("message", "达咩！接口被限流了！");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
