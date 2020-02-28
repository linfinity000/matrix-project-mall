package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class Brand implements Serializable {

    @TableId
    private String brandId;

    private String brandName;

    private String brandLogo;

    private String brandLink;

    private String shopId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
