package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
@TableName("goods_sku")
public class GoodsSku implements Serializable {

    @TableId
    private String id;

    private String goodsId;

    private String atomsGoodsSkuLabelId;

    private String skuValue;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}