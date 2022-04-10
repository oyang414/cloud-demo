package demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import demo.entity.Storage;
import demo.mapper.StorageMapper;
import demo.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:41 2022/3/29
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> reduceStock(String code, Integer num) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",false);
        Storage storage = storageMapper.selectOne(new QueryWrapper<Storage>().eq("code",code));
        // 仓库减少库存
        Integer remain = storage.getStock() - num;
        storage.setStock(remain);
        storage.setUpdateTime(LocalDateTime.now());
        storageMapper.updateById(storage);
        // 模拟异常
        if (storage.getStock() % 2 == 0) {
            log.error(">>>> 仓库分支事务抛出异常 <<<<");
            throw new RuntimeException("仓库分支事务抛出异常");
        }

        resultMap.put("success",true);
        resultMap.put("message","更新库存成功");
        return resultMap;
    }
}
