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
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
@TableName("menu")
public class Menu implements Serializable {

    @TableId
    private String menuId;

    private String menuName;

    private Integer isDefault;

    private String url;

    private String parentId;

    private String type;

    private Integer orderBy;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private Integer status;
}
