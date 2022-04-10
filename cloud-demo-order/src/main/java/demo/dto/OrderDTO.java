package demo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ouyangxingjie
 * @Description
 * @Date 20:13 2022/3/29
 */
@Data
public class OrderDTO {
    private String code;
    private Integer nums;
    private String address;
    private BigDecimal totalPrice;
    private Integer orderNum;
}
