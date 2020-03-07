package matrix.project.mall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.based.serializer.DateTimeSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Data
@Accessors(chain = true)
public class OrderDto implements Serializable {

    private String orderId;

    private String shopName;

    private BigDecimal orderPrice;

    private Integer orderGoodsCount;

    private Integer hasLogistics;

    private Integer provinceCode;

    private Integer cityCode;

    private Integer areaCode;

    private String provinceName;

    private String cityName;

    private String areaName;

    private String address;

    private String logisticsCompanyName;

    private String logisticsNo;

    private String remark;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer orderStatus;

}
