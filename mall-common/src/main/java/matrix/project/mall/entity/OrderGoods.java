package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
@TableName("order_goods")
public class OrderGoods implements Serializable {

    @TableId
    private String id;

    private String orderId;

    private String goodsId;

    private Long goodsCount;

    private BigDecimal goodsTotalPrice;

    private String goodsSecret;

    private String mirror;
}
