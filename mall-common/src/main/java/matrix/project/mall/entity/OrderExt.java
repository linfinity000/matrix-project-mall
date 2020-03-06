package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Data
@Accessors(chain = true)
@TableName("order_ext")
public class OrderExt implements Serializable {

    private String id;

    private String orderId;

    private String logisticsCompanyId;

    private String logisticsCompanyName;

    private String logisticsNo;

    private Long countryCode;

    private String countryName;

    private Long provinceCode;

    private String provinceName;

    private Long cityCode;

    private String cityName;

    private String address;

    private String remark;
}
