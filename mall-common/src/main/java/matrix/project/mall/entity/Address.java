package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Data
@Accessors(chain = true)
@TableName("address")
public class Address implements Serializable {

    private String addressId;

    private String address;

    private Long provinceCode;

    private String provinceName;

    private Long cityCode;

    private String cityName;

    private Long areaCode;

    private String areaName;

    private String userId;

    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
