package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
public class UserVo implements Serializable {

    private String username;

    private String nickname;

    private String password;

    private String mobile;

}
