package demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:17 2022/3/29
 */
@TableName("tb_order")
@Data
public class Order {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id ;
    private String address;
    private BigDecimal totalPrice;
    private Integer orderNum;
    private LocalDateTime createTime;
}
