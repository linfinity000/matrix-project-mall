package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
@TableName("logistics")
public class Logistics implements Serializable {

    @TableId
    private String logisticsId;

    private String logisticsName;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
