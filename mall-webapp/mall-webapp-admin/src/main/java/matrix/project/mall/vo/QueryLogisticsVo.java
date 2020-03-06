package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
public class QueryLogisticsVo implements Serializable {

    private String logisticsName;

    private Integer page;

    private Integer pageSize;

}
