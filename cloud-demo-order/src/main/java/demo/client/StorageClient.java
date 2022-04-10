package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description StorageClient
 * @Date 16:57 2022/3/3
 */
@FeignClient(name = "cloud-demo-storage" )
public interface StorageClient {


    @GetMapping(value = "/storage/reduce")
    Map<String,Object> reduce(@RequestParam("code") String code, @RequestParam("nums") Integer nums);


    @GetMapping(value = "/storageTcc/insert")
    String insert(@RequestParam("code") String code);
}
