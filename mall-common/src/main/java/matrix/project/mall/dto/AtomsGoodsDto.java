package matrix.project.mall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.based.serializer.DateTimeSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@Data
@Accessors(chain = true)
public class AtomsGoodsDto implements Serializable {

    private String atomsGoodsId;

    private String atomsGoodsName;

    private String salePoints;

    private String description;

    private String atomsGoodsImage;

    private String shopId;

    private String shopName;

    private String brandId;

    private String brandName;

    private String categoryId;

    private String categoryName;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer status;
}
