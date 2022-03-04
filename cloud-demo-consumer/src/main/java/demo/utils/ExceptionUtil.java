package demo.utils;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @Author ouyangxingjie
 * @Description ExceptionUtil全局降级处理
 * @Date 15:24 2022/3/3
 */
public class ExceptionUtil {
    // 服务流量控制处理
    public static ClientHttpResponse handleException(HttpRequest request,
                                                     byte[] body,
                                                     ClientHttpRequestExecution execution,
                                                     BlockException exception) {
        System.err.println("ExceptionUtil.handleException...");
        return new SentinelClientHttpResponse("restTemplate handleException: 流量控制");
    }

    // 服务熔断降级处理
    public static ClientHttpResponse fallback(HttpRequest request,
                                              byte[] body,
                                              ClientHttpRequestExecution execution,
                                              BlockException exception) {
        System.err.println("ExceptionUtil.fallback...");
        return new SentinelClientHttpResponse("restTemplate fallback: 熔断降级");
    }
}
