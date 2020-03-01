package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.CategoryDto;
import matrix.project.mall.entity.Category;
import matrix.project.mall.vo.CategoryVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface CategoryService extends IService<Category> {

    List<CategoryDto> queryTree();

    boolean saveTree(CategoryVo categoryVo);

    boolean removeTree(String categoryId);

    Category queryById(String categoryId);

    Integer countByShopId(String shopId);
}
