package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
public class AdminUserVo implements Serializable {

    private String userId;

    private String username;

    private String password;

    private String shopId;

    private Integer status;
}
