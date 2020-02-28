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
@TableName("admin_user")
public class AdminUser implements Serializable {

    @TableId
    private String userId;

    private String username;

    private String password;

    private Integer isDefault;

    private String shopId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}
