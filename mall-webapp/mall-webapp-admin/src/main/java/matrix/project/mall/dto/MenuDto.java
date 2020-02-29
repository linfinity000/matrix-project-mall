package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.common.utils.TreeUtil;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
public class MenuDto extends TreeUtil.Tree<MenuDto> {

    private String menuId;

    private String menuName;

    private String url;

    private String parentId;

    private Integer isDefault;

    private Integer orderBy;
}
