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
public class Category implements Serializable {

    @TableId
    private String categoryId;

    private String categoryName;

    private String parentId;

    private String shopId;

    private Date createTime;

    private Date updateTime;

    private Integer status;


}
