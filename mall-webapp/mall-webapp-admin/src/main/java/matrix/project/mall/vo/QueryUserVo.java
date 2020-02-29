package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
public class QueryUserVo implements Serializable {

    private String username;

    private Integer status;

    private Integer page;

    private Integer pageSize;
}
