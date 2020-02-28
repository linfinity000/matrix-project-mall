package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
@TableName("goods")
public class Goods implements Serializable {

    @TableId
    private String goodsId;

    private String atomsGoodsId;

    private BigDecimal originPrice;

    private BigDecimal salePrice;

    private Integer stock;

    private String imageUrl;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
