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
public class CategoryVo implements Serializable {

    private String categoryId;

    private String categoryName;

    private String parentId;
}
