package demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ouyangxingjie
 * @Description ProviderClient
 * @Date 16:57 2022/3/3
 */
//如果提供方的接口用了@SentinelResource,优先级会比该处的fallback高
//@FeignClient(name = "cloud-demo-provider" ,fallback = ProviderFallback.class)
@FeignClient(name = "cloud-demo-provider" )
public interface ProviderClient {

    /**
     * @Author ouyangxingjie
     * @Description provider服务接口的声明
     * @Date 17:13 2022/3/3
     * @param name 姓名
     * @return java.lang.String
     */
    @GetMapping(value = "/provider/echo")
    String echo(@RequestParam("name") String name);

}
