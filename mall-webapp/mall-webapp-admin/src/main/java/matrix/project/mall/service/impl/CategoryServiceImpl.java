package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.module.common.utils.TreeUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.CategoryDto;
import matrix.project.mall.entity.Category;
import matrix.project.mall.entity.Shop;
import matrix.project.mall.mapper.CategoryMapper;
import matrix.project.mall.service.AtomsGoodsService;
import matrix.project.mall.service.CategoryService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.utils.ListUtil;
import matrix.project.mall.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @Override
    public List<CategoryDto> queryTree() {
        Shop shop = shopService.getShop();
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("SHOP_ID", shop.getShopId());
        queryWrapper.orderByAsc("CREATE_TIME");
        List<Category> categories = list(queryWrapper);
        if (CollectionUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }
        List<CategoryDto> result = ListUtil.copyList(categories, CategoryDto.class);
        TreeUtil.toTree(result, new TreeUtil.Comparator<CategoryDto>() {
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
        return result;
    }

    @Override
    public boolean saveTree(CategoryVo categoryVo) {
        Assert.state(!StringUtils.isEmpty(categoryVo.getCategoryName()), "分类名不允许为空");
        Shop shop = shopService.getShop();
        Category category = null;
        if (!StringUtils.isEmpty(categoryVo.getCategoryId())) {
            category = queryById(categoryVo.getCategoryId());
            Assert.state(category != null, "菜单未找到");
        } else {
            category = new Category()
                    .setCategoryId(RandomUtil.getUUID())
                    .setParentId(StringUtils.isEmpty(categoryVo.getParentId()) ? "0" : categoryVo.getParentId())
                    .setCreateTime(new Date())
                    .setStatus(Constant.ENABLED);
        }
        assert category != null;
        category.setCategoryName(categoryVo.getCategoryName())
                .setShopId(shop.getShopId())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(categoryVo.getCategoryId())) {
            updateById(category);
        } else {
            save(category);
        }
        return true;
    }

    @Override
    public boolean removeTree(String categoryId) {
        Category category = queryById(categoryId);
        Assert.state(category != null, "未找到分类");
        assert category != null;
        Assert.state(atomsGoodsService.countByCategoryId(categoryId) <= 0, "分类下存在未删除商品");
        category.setStatus(Constant.DELETED);
        updateById(category);
        return true;
    }

    @Override
    public Category queryById(String categoryId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CATEGORY_ID", categoryId)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

    @Override
    public Integer countByShopId(String shopId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SHOP_ID", shopId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }
}
