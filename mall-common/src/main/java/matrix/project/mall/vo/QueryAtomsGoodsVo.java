package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@Data
@Accessors(chain = true)
public class QueryAtomsGoodsVo implements Serializable {

    private String atomsGoodsName;

    private Integer status;

    private Integer page;

    private Integer pageSize;

}
