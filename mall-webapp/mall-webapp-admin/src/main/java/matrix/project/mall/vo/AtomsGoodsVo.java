package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@Data
@Accessors(chain = true)
public class AtomsGoodsVo implements Serializable {

    private String atomsGoodsId;

    private String atomsGoodsName;

    private String atomsGoodsImage;

    private String brandId;

    private String categoryId;

    private String description;

    private String salePoints;

    private Integer hasLogistics;

    private Integer status;

    private List<AttrLabel> attrList;

    private List<SkuLabel> skuList;

    private List<Banner> banners;

    @Data
    @Accessors(chain = true)
    public static class AttrLabel implements Serializable {

        private String attrName;

    }

    @Data
    @Accessors(chain = true)
    public static class SkuLabel implements Serializable {

        private String skuName;

    }

    @Data
    @Accessors(chain = true)
    public static class Banner implements Serializable {

        private String imageUrl;

    }
}
