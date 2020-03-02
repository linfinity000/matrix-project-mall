package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcheng
 * @date 2019/6/8
 */
@Data
@Accessors(chain = true)
public class SkuDto implements Serializable {

    private String labelId;

    private String labelName;

    private List<String> skuValues;

    @Data
    @Accessors(chain = true)
    public static class SkuLabelDto implements Serializable {
        private String labelId;

        private String labelName;

        private String skuValue;
    }
}
