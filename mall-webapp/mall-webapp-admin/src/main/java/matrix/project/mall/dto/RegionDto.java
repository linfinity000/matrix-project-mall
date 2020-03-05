package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.common.utils.TreeUtil;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
public class RegionDto extends TreeUtil.Tree<RegionDto> {

    private Long code;

    private String name;

    private Long parentCode;
}
