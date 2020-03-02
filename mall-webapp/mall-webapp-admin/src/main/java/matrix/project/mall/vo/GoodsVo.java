package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@Data
@Accessors(chain = true)
public class GoodsVo implements Serializable {

    private String atomsGoodsId;

    private String goodsId;

    private BigDecimal originPrice;

    private BigDecimal salePrice;

    private Integer stock;

    private String imageUrl;

    private Integer status;

    private List<SkuLabel> skuLabels;

    private List<AttrLabel> attrLabels;

    @Data
    @Accessors(chain = true)
    public static class SkuLabel implements Serializable {

        private String labelId;

        private String skuValue;

    }

    @Data
    @Accessors(chain = true)
    public static class AttrLabel implements Serializable {

        private String labelId;

        private String attrValue;

    }
}
