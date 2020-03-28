package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-21
 */
@Data
@Accessors(chain = true)
public class QueryLogVo implements Serializable {

    private String name;

    private String userId;

    private String remark;

    private Integer page;

    private Integer pageSize;
}
