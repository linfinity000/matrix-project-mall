package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Shop;
import matrix.project.mall.mapper.ShopMapper;
import matrix.project.mall.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

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
    public String queryDefaultShopId() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
            .eq("IS_DEFAULT", 1);
        return getOne(queryWrapper, false).getShopId();
    }

}
