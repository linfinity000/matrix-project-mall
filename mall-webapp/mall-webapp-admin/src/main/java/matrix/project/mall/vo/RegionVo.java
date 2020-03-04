package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Data
@Accessors(chain = true)
public class RegionVo implements Serializable {

    private Long code;

    private String name;

    private Long parentCode;
}
