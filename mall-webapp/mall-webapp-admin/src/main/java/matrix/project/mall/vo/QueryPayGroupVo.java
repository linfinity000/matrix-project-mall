package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Data
@Accessors(chain = true)
public class QueryPayGroupVo implements Serializable {

    private String orderId;

    private Integer status;

    private Integer page;

    private Integer pageSize;
}
