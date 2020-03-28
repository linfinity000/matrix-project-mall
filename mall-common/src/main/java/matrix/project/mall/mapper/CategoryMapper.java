package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.CategoryItemDto;
import matrix.project.mall.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryItemDto> listCategoryItem(@Param("shopId") String shopId);

}
