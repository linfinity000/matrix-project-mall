package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.CategoryDto;
import matrix.project.mall.dto.CategoryItemDto;
import matrix.project.mall.entity.Category;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface CategoryService extends IService<Category> {

    List<CategoryDto> queryByShopId(String shopId);

    CategoryDto queryByCategoryId(String categoryId, boolean isTree);

    List<CategoryItemDto> listCategoryItem(String shopId);
}
