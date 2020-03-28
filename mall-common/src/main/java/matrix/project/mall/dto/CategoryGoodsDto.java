package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Data
@Accessors(chain = true)
public class CategoryGoodsDto implements Serializable {

    private String categoryId;

    private String categoryName;

    private List<GoodsItemDto> goodsItems;

    @Data
    @Accessors(chain = true)
    public static class GoodsItemDto implements Serializable {

        private String categoryId;

        private String categoryName;

        private String goodsId;

        private String salePoints;

        private String goodsName;

        private Integer goodsCount = 0;

        private String imageUrl;

        private Integer stock;

        private BigDecimal price;
    }
}
