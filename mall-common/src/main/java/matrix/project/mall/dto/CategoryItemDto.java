package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-22
 */
@Data
@Accessors(chain = true)
public class CategoryItemDto implements Serializable {

    private String id;

    private String name;

    private Integer goodsCount;
}
