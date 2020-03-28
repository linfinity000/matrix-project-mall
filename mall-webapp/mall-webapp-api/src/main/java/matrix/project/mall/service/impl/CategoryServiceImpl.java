package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.TreeUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.CategoryDto;
import matrix.project.mall.dto.CategoryItemDto;
import matrix.project.mall.entity.Category;
import matrix.project.mall.mapper.CategoryMapper;
import matrix.project.mall.service.CategoryService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.utils.ListUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ShopService shopService;

    @Override
    public List<CategoryDto> queryByShopId(String shopId) {
        if (StringUtils.isEmpty(shopId)) {
            shopId = shopService.queryDefaultShopId();
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("SHOP_ID", shopId);
        List<Category> categories = list(queryWrapper);
        if (CollectionUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }
        List<CategoryDto> list = ListUtil.copyList(categories, CategoryDto.class);
        TreeUtil.toTree(list, new TreeUtil.Comparator<CategoryDto>() {
            @Override
            public boolean isParentWithChild(CategoryDto pre, CategoryDto after) {
                if (pre.getCategoryId().equals(after.getParentId())) {
                    return true;
                }
                return false;
            }
            @Override
            public boolean isTop(CategoryDto categoryDto) {
                String parentId = categoryDto.getParentId();
                if (!StringUtils.isEmpty(parentId) && parentId.equals("0")) {
                    return true;
                }
                return false;
            }
        });
        return list;
    }

    @Override
    public CategoryDto queryByCategoryId(String categoryId, boolean isTree) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("CATEGORY_ID", categoryId);
        Category category = getOne(queryWrapper, false);
        Assert.state(category != null, "分类未找到");
        assert category != null;
        if (!isTree) {
            CategoryDto result = new CategoryDto();
            BeanUtils.copyProperties(category, result);
            return result;
        }
        return findRecursionTreeByCategoryId(queryByShopId(category.getShopId()), categoryId);
    }

    @Override
    public List<CategoryItemDto> listCategoryItem(String shopId) {
        return getBaseMapper().listCategoryItem(shopId);
    }

    private CategoryDto findRecursionTreeByCategoryId(List<CategoryDto> treeCategories, String categoryId) {
        if (CollectionUtils.isEmpty(treeCategories)) {
            return null;
        }
        for (CategoryDto category: treeCategories) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
            CategoryDto result = findRecursionTreeByCategoryId(category.getChildren(), categoryId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}
