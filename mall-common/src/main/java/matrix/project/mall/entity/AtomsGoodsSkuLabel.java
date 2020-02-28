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
@TableName("atoms_goods_sku_label")
public class AtomsGoodsSkuLabel implements Serializable {

    private String id;

    private String atomsGoodsId;

    private String skuName;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
