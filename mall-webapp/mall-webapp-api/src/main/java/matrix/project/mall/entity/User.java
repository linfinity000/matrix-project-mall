package matrix.project.mall.entity;

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
public class User implements Serializable {

    private String userId;

    private String username;

    private String nickname;

    private String mobile;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
