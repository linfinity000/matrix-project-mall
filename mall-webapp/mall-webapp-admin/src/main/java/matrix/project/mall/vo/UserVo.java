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
public class UserVo implements Serializable {

    private String userId;

    private String username;

    private String nickname;

    private String mobile;

    private String password;

    private Integer status;

}
