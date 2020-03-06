package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
public class GoodsNameDto implements Serializable {

    private String goodsId;

    private String atomsGoodsName;

    private String goodsName;
}
