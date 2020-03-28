package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.based.serializer.DateTimeSerializer;

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

    @TableId
    private String id;

    private String orderIds;

    private String shopIds;

    private BigDecimal price;

    private String payMode;

    private Integer payChannel;

    private String userId;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer status;
}
