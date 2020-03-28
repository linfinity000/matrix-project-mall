package matrix.project.mall.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Data
@Accessors(chain = true)
public class OrderPayGroup implements Serializable {

    private String id;

    private String orderIds;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
