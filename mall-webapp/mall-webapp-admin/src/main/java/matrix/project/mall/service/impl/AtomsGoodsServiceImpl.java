package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.mapper.AtomsGoodsMapper;
import matrix.project.mall.service.AtomsGoodsService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class AtomsGoodsServiceImpl extends ServiceImpl<AtomsGoodsMapper, AtomsGoods> implements AtomsGoodsService {

    @Override
    public Integer countByShopId(String shopId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SHOP_ID", shopId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countByCategoryId(String categoryId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CATEGORY_ID", categoryId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countByBrandId(String brandId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BRAND_ID", brandId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

}
