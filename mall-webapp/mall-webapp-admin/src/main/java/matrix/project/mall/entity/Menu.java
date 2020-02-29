package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
@TableName("menu")
public class Menu implements Serializable {

    @TableId
    private String menuId;

    private String menuName;

    private Integer isDefault;

    private String url;

    private String parentId;

    private String type;

    private Integer orderBy;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
