package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-14
 */
@Data
@Accessors(chain = true)
public class PageDto<T> implements Serializable {

    private Integer count;

    private List<T> list;
}
