package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
@TableName("`order`")
public class Order implements Serializable {

    @TableId
    private String orderId;

    private String shopId;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
