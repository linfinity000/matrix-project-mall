package matrix.project.mall.dto;

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
public class MirrorDto implements Serializable {

    private AtomsGoods atomsGoods;

    private Goods goods;

    private String goodsName;

    @Data
    @Accessors(chain = true)
    public static class AtomsGoods implements Serializable {
        private String atomsGoodsId;

        private String atomsGoodsName;

        private String salePoints;

        private String description;

        private String atomsGoodsImage;

        private String shopId;

        private String brandId;

        private String categoryId;

        private Integer hasLogistics;

        private String createTime;

        private String updateTime;
    }

    @Data
    @Accessors(chain = true)
    public static class Goods implements Serializable {
        private String goodsId;

        private String atomsGoodsId;

        private BigDecimal originPrice;

        private BigDecimal salePrice;

        private Integer stock;

        private String imageUrl;

        private String createTime;

        private String updateTime;
    }
}
