package matrix.project.mall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.based.serializer.DateTimeSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-14
 */
@Data
@Accessors(chain = true)
public class ApiOrderDto implements Serializable {

    private String orderId;

    private String shopName;

    private BigDecimal orderPrice;

    private BigDecimal eatPoint;

    private Integer orderGoodsCount;

    private Integer orderStatus;

    private String orderStatusRemark;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    private List<OrderGoodsDto> orderGoodsList;

    @Data
    @Accessors(chain = true)
    public static class OrderGoodsDto implements Serializable {

        private String orderId;

        private String goodsName;

        private Integer goodsCount;

        private BigDecimal goodsTotalPrice;

        private BigDecimal eatPoint;
    }
}
