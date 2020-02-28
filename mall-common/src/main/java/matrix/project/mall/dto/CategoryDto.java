package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.common.utils.TreeUtil;

import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
public class CategoryDto extends TreeUtil.Tree<CategoryDto> {

    private String categoryId;

    private String categoryName;

    private String parentId;

    private String shopId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
