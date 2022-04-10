package demo.controller;


import demo.service.StorageTccService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description storgaeTcc Controller接口
 * @Date 14:40 2022/4/10
 */
@RestController
@RequestMapping("/storageTcc")
public class StorageTccController {
    @Autowired
    private StorageTccService storageTccService;

    @GetMapping("/insert")
    @GlobalTransactional
    public String insert(@RequestParam("code") String code) {
        Map<String ,String> params = new HashMap<>();
        params.put("code",code);
        return storageTccService.insert(params);
    }
}
