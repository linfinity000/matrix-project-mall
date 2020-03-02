package matrix.project.mall.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Data
@Accessors(chain = true)
public class City implements Serializable {

    private String code;

    private String name;
}