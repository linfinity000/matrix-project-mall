package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Data
@Accessors(chain = true)
public class CartVo implements Serializable {

    private String goodsId;

    /**
     * 直接传入数量，赋值变量，不做加减(前端做下处理)
     */
    private Integer goodsCount;

}
