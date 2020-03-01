package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Brand;
import matrix.project.mall.mapper.BrandMapper;
import matrix.project.mall.service.BrandService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.BrandVo;
import matrix.project.mall.vo.QueryBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    public Integer countByShopId(String shopId) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SHOP_ID", shopId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countBrand(QueryBrandVo queryBrandVo) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryBrandVo.getBrandName())) {
            queryWrapper.like("BRAND_NAME", "%" + queryBrandVo.getBrandName() + "%");
        }
        queryWrapper.eq("SHOP_ID", shopService.getShop().getShopId())
                .eq("STATUS", Constant.ENABLED);
        return count(queryWrapper);
    }

    @Override
    public List<Brand> listBrand(QueryBrandVo queryBrandVo) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryBrandVo.getBrandName())) {
            queryWrapper.like("BRAND_NAME", "%" + queryBrandVo.getBrandName() + "%");
        }
        queryWrapper.eq("SHOP_ID", shopService.getShop().getShopId())
                .eq("STATUS", Constant.ENABLED);
        queryWrapper.orderByDesc("UPDATE_TIME");
        IPage<Brand> page = new Page<>(queryBrandVo.getPage(), queryBrandVo.getPageSize());
        return page(page, queryWrapper).getRecords();
    }

    @Override
    public boolean saveBrand(BrandVo brandVo) {
        Assert.state(!StringUtils.isEmpty(brandVo.getBrandName()), "品牌名称不允许为空");
        Brand brand = null;
        if (!StringUtils.isEmpty(brandVo.getBrandId())) {
            brand = queryByBrandId(brandVo.getBrandId());
            Assert.state(brand != null, "品牌未找到");
        } else {
            brand = new Brand()
                    .setBrandId(RandomUtil.getUUID())
                    .setShopId(shopService.getShop().getShopId())
                    .setCreateTime(new Date())
                    .setStatus(Constant.ENABLED);
        }
        assert brand != null;
        brand.setBrandName(brandVo.getBrandName())
                .setBrandUrl(brandVo.getBrandUrl())
                .setBrandLogo(brandVo.getBrandLogo())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(brandVo.getBrandId())) {
            updateById(brand);
        } else {
            save(brand);
        }
        return true;
    }

    @Override
    public boolean removeBrand(String brandId) {
        Brand brand = queryByBrandId(brandId);
        Assert.state(brand != null, "未找到品牌");
        assert brand != null;
        brand.setStatus(Constant.DELETED);
        updateById(brand);
        return true;
    }

    @Override
    public Brand queryByBrandId(String brandId) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BRAND_ID", brandId)
                .ne("STATUS", Constant.DELETED);
        return getOne(queryWrapper, false);
    }
}
