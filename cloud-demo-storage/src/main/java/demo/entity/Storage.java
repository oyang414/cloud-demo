package demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:43 2022/3/29
 */
@Data
@TableName("tb_storage")
public class Storage {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id ;
    private String code;
    private Integer stock;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
