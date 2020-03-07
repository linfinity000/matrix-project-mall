package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Data
@Accessors(chain = true)
public class ShipVo implements Serializable {

    private String orderGoodsId;

    private String logisticsCompanyId;

    private String logisticsNo;

    private String goodsSecret;
}
