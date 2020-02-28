package matrix.project.mall.entity;

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
@TableName("atoms_goods")
public class AtomsGoods implements Serializable {

    private String atomsGoodsId;

    private String atomsGoodsName;

    private String salePoints;

    private String description;

    private String atomsGoodsImage;

    private String shopId;

    private String brandId;

    private String categoryId;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
