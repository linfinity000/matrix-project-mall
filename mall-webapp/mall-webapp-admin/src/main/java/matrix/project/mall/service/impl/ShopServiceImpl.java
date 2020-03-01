package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.AdminUserDto;
import matrix.project.mall.entity.Shop;
import matrix.project.mall.mapper.ShopMapper;
import matrix.project.mall.service.AtomsGoodsService;
import matrix.project.mall.service.BrandService;
import matrix.project.mall.service.CategoryService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.vo.QueryShopVo;
import matrix.project.mall.vo.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Override
    public Shop getShop() {
        AdminUserDto adminUserDto = LoginUtil.getAdminUser();
        if (!StringUtils.isEmpty(adminUserDto.getShopId())) {
            return queryByShopId(adminUserDto.getShopId());
        } else {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String shopId = valueOperations.get("bind-shop:" + LoginUtil.getAdminUser().getUserId());
            if (StringUtils.isEmpty(shopId)) {
                return queryDefaultShop();
            } else {
                Shop shop = queryByShopId(shopId);
                Assert.state(shop != null, "绑定店铺已不存在，请重新绑定");
                return shop;
            }
        }
    }

    @Override
    public boolean bindShop(String shopId) {
        Assert.state(StringUtils.isEmpty(LoginUtil.getAdminUser().getShopId()), "只有管理员可以绑定店铺");
        Shop shop = queryByShopId(shopId);
        Assert.state(shop != null, "店铺不存在");
        assert shop != null;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "bind-shop:" + LoginUtil.getAdminUser().getUserId();
        valueOperations.set(key, shopId);
        return true;
    }

    @Override
    public List<Shop> listShop(QueryShopVo queryShopVo) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryShopVo.getShopName())) {
            queryWrapper.like("SHOP_NAME", "%" + queryShopVo.getShopName() + "%");
        }
        if (queryShopVo.getStatus() != null) {
            queryWrapper.eq("STATUS", queryShopVo.getStatus());
        } else {
            queryWrapper.ne("STATUS", Constant.DELETED);
        }
        queryWrapper.orderByAsc("UPDATE_TIME");
        IPage<Shop> page = new Page<>(queryShopVo.getPage(), queryShopVo.getPageSize());
        return page(page, queryWrapper).getRecords();
    }

    @Override
    public Integer countShop(QueryShopVo queryShopVo) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryShopVo.getShopName())) {
            queryWrapper.like("SHOP_NAME", "%" + queryShopVo.getShopName() + "%");
        }
        if (queryShopVo.getStatus() != null) {
            queryWrapper.eq("STATUS", queryShopVo.getStatus());
        } else {
            queryWrapper.ne("STATUS", Constant.DELETED);
        }
        return count(queryWrapper);
    }

    @Override
    public Shop queryByShopId(String shopId) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        if (StringUtils.isEmpty(shopId)) {
            queryWrapper.eq("IS_DEFAULT", 1);
        } else {
            queryWrapper.eq("SHOP_ID", shopId);
        }
        return getOne(queryWrapper, false);
    }

    @Override
    public Shop queryDefaultShop() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("IS_DEFAULT", 1);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<Shop> listValidShop() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        queryWrapper.orderByAsc("UPDATE_TIME");
        return list(queryWrapper);
    }

    @Override
    public boolean saveShop(ShopVo shopVo) {
        Assert.state(!StringUtils.isEmpty(shopVo.getShopName()), "店铺名称不允许为空");
        Assert.state(shopVo.getShopStar() != null, "店铺星级不允许为空");
        Assert.state(shopVo.getStatus() != null, "状态不允许为空");
        boolean isAdmin = StringUtils.isEmpty(LoginUtil.getAdminUser().getShopId());
        Shop shop = null;
        if (!StringUtils.isEmpty(shopVo.getShopId())) {
            shop = queryByShopId(shopVo.getShopId());
            Assert.state(shop != null, "店铺未找到");
        } else {
            Assert.state(isAdmin, "普通用户不能新建店铺");
            shop = new Shop()
                    .setShopId(RandomUtil.getUUID())
                    .setIsDefault(0)
                    .setCreateTime(new Date());
        }
        assert shop != null;
        if (shop.getIsDefault().equals(0) && isAdmin) {
            shop.setStatus(shopVo.getStatus());
        }
        if (isAdmin) {
            shop.setShopStar(shopVo.getShopStar());
        }
        shop.setShopName(shopVo.getShopName())
                .setShopLogo(shopVo.getShopLogo())
                .setShopDesc(shopVo.getShopDesc())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(shopVo.getShopId())) {
            updateById(shop);
        } else {
            save(shop);
        }
        return true;
    }

    @Override
    public boolean removeShop(String shopId) {
        Shop shop = queryByShopId(shopId);
        Assert.state(shop != null, "未找到店铺");
        assert shop != null;
        Assert.state(shop.getIsDefault().equals(0), "默认店铺不允许删除");
        Assert.state(atomsGoodsService.countByShopId(shopId) <= 0, "店铺下存在原子商品");
        Assert.state(categoryService.countByShopId(shopId) <= 0, "店铺下存在分类");
        Assert.state(brandService.countByShopId(shopId) <= 0, "店铺下存在品牌");
        shop.setStatus(Constant.DELETED);
        updateById(shop);
        return true;
    }
}
