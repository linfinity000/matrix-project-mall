package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
@TableName("shop")
public class Shop implements Serializable {

    @TableId
    private String shopId;

    private String shopName;

    private String shopLogo;

    private String shopDesc;

    private Integer shopStar;

    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
