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
 * @date 2020-02-28
 */
@Data
@Accessors(chain = true)
@TableName("atoms_goods_attr_label")
public class AtomsGoodsAttrLabel implements Serializable {

    @TableId
    private String id;

    private String atomsGoodsId;

    private String attrName;

    private Integer orderBy;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer status;

}
