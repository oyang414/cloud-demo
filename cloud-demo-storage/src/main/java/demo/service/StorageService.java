package demo.service;

import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:41 2022/3/29
 */
public interface StorageService {

    Map<String, Object> reduceStock(String code, Integer num);
}
