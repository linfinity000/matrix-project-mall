package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.MenuList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Mapper
@TargetDataSource("admin")
public interface MenuListMapper extends BaseMapper<MenuList> {
}
