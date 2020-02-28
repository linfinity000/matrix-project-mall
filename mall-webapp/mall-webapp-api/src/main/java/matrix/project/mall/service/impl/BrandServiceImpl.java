package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Brand;
import matrix.project.mall.mapper.BrandMapper;
import matrix.project.mall.service.BrandService;
import matrix.project.mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private ShopService shopService;

    @Override
    public List<Brand> queryByShopId(String shopId) {
        if (StringUtils.isEmpty(shopId)) {
            shopId = shopService.queryDefaultShopId();
        }
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("SHOP_ID", shopId)
                .orderByDesc("CREATE_TIME");
        return list(queryWrapper);
    }

    @Override
    public Brand queryByBrandId(String brandId) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("BRAND_ID", brandId);
        return getOne(queryWrapper, false);
    }
}
