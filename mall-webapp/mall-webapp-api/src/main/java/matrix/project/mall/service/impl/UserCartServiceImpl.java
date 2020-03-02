package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.entity.UserCart;
import matrix.project.mall.mapper.UserCartMapper;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.service.UserCartService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Service
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart> implements UserCartService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public boolean updateCart(CartVo cartVo) {
        Assert.state(!StringUtils.isEmpty(cartVo.getGoodsId()), "商品ID不允许为空");
        Assert.state(cartVo.getGoodsCount() != null, "商品数量不允许为空");
        assert cartVo.getGoodsCount() != null;
        Assert.state(cartVo.getGoodsCount() > 0, "商品数量必须大于0");
        Goods goods = goodsService.queryByGoodsId(cartVo.getGoodsId());
        Assert.state(goods.getStock() >= cartVo.getGoodsCount(), "商品库存不足");
        Date date = new Date();
        UserCart userCart = queryByGoodsId(cartVo.getGoodsId());
        if (userCart == null) {
            userCart = new UserCart()
                    .setId(RandomUtil.getUUID())
                    .setUserId(LoginUtil.getUser().getUserId())
                    .setGoodsId(cartVo.getGoodsId())
                    .setGoodsCount(cartVo.getGoodsCount())
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setStatus(Constant.ENABLED);
            save(userCart);
        } else {
            userCart.setGoodsCount(cartVo.getGoodsCount())
                    .setUpdateTime(date);
            updateById(userCart);
        }
        return true;
    }

    @Override
    public UserCart queryByGoodsId(String goodsId) {
        QueryWrapper<UserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId)
                .eq("USER_ID", LoginUtil.getUser().getUserId())
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

}
