package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2019/6/8
 */
@Data
@Accessors(chain = true)
public class AttrDto implements Serializable {

    private String labelId;

    private String labelName;

    private String attrValue;
}
