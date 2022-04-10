package demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.entity.Storage;
import org.springframework.stereotype.Component;

@Component("srderMapper")
public interface StorageMapper extends BaseMapper<Storage> {
}