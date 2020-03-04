package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Data
@Accessors(chain = true)
public class Region implements Serializable {

    @TableId
    private Long code;

    private String name;

    private Long parentCode;

    private Integer status;
}
