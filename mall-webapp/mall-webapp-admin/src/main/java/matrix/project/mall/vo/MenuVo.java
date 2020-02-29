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
public class MenuVo implements Serializable {

    private String menuId;

    private String menuName;

    private String parentId;

    private String url;

    private Integer orderBy;
}
