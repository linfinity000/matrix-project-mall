package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2019/6/8
 */
@Data
@Accessors(chain = true)
public class QuerySkuLabelVo implements Serializable {

    private String labelId;

    private String skuValue;
}
