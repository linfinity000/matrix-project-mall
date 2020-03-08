package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Data
@Accessors(chain = true)
public class OrderAddressVo implements Serializable {

    private String orderId;

    private List<Long> regions;

    private String address;

    private String linkName;

    private String mobile;
}
