package matrix.project.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.based.serializer.DateTimeSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Data
@Accessors(chain = true)
@TableName("address")
public class Address implements Serializable {

    @TableId
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

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer status;
}
