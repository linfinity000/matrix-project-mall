package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-14
 */
@Data
@Accessors(chain = true)
public class QueryOrderListVo implements Serializable {

    private Integer page;

    private Integer pageSize;
}
