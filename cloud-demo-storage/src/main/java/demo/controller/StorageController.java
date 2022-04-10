package demo.controller;

import demo.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:39 2022/3/29
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    private StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/reduce")
    Map<String,Object> reduce(@RequestParam("code") String code, @RequestParam("nums") Integer nums){
        return storageService.reduceStock(code,nums);
    }

}
