package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.AdminUserDto;
import matrix.project.mall.entity.Shop;
import matrix.project.mall.mapper.ShopMapper;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.vo.QueryShopVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Override
    public Shop getShop() {
        AdminUserDto adminUserDto = LoginUtil.getAdminUser();
        if (!StringUtils.isEmpty(adminUserDto.getShopId())) {
            return queryByShopId(adminUserDto.getShopId());
        }
        return queryDefaultShop();
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
}
