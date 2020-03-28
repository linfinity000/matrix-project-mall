package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.UserCartDto;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.entity.UserCart;
import matrix.project.mall.mapper.UserCartMapper;
import matrix.project.mall.service.AtomsGoodsService;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.service.UserCartService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Service
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart> implements UserCartService {

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public boolean saveCart(CartVo cartVo) {
        Assert.state(!StringUtils.isEmpty(cartVo.getGoodsId()), "商品ID不允许为空");
        Assert.state(cartVo.getGoodsCount() != null, "商品数量不允许为空");
        assert cartVo.getGoodsCount() != null;
        Assert.state(cartVo.getGoodsCount() > 0, "商品数量必须大于0");
        Goods goods = goodsService.queryByGoodsId(cartVo.getGoodsId());
        Assert.state(goods != null, "商品不可用");
        assert goods != null;
        Assert.state(goods.getStock() >= cartVo.getGoodsCount(), "商品库存不足");
        AtomsGoods atomsGoods = atomsGoodsService.queryById(goods.getAtomsGoodsId());
        Assert.state(atomsGoods != null, "原子商品不可用");
        Date date = new Date();
        UserCart userCart = queryByGoodsId(cartVo.getGoodsId());
        if (userCart == null) {
            assert atomsGoods != null;
            userCart = new UserCart()
                    .setId(RandomUtil.getUUID())
                    .setUserId(LoginUtil.getUser().getUserId())
                    .setShopId(atomsGoods.getShopId())
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

    @Override
    public UserCart queryById(String id) {
        QueryWrapper<UserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", id)
                .eq("USER_ID", LoginUtil.getUser().getUserId())
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<UserCart> queryByIds(List<String> ids) {
        QueryWrapper<UserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ID", ids)
                .eq("USER_ID", LoginUtil.getUser().getUserId())
                .gt("GOODS_COUNT", 0)
                .eq("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

    @Override
    public boolean removeCart(String id) {
        UserCart userCart = queryById(id);
        Assert.state(userCart != null, "查询购物车为空");
        assert userCart != null;
        userCart.setStatus(Constant.DELETED);
        updateById(userCart);
        return true;
    }

    @Override
    public List<UserCartDto> listCart() {
        List<UserCartDto.UserCartItemDto> userCartItems = getBaseMapper().listCartItems(LoginUtil.getUser().getUserId());
        return convertUserCart(userCartItems);
    }

    @Override
    public Integer cartCount() {
        return getBaseMapper().cartCount(LoginUtil.getUser().getUserId());
    }

    @Override
    public List<UserCartDto> cartsInfo(List<String> cartIds) {
        Assert.state(!CollectionUtils.isEmpty(cartIds), "购物车ID不允许为空");
        List<UserCartDto.UserCartItemDto> userCartItems = getBaseMapper().listCartItemsByCartIds(LoginUtil.getUser().getUserId(), cartIds);
        return convertUserCart(userCartItems);
    }

    private List<UserCartDto> convertUserCart(List<UserCartDto.UserCartItemDto> userCartItems) {
        if (CollectionUtils.isEmpty(userCartItems)) {
            return new ArrayList<>();
        }
        Map<String, String> shopNameDict = userCartItems.stream().collect(Collectors.toMap(UserCartDto.UserCartItemDto::getShopId, UserCartDto.UserCartItemDto::getShopName, (o1, o2) -> o2));
        Map<String, List<UserCartDto.UserCartItemDto>> itemDict = new LinkedHashMap<>();
        for (UserCartDto.UserCartItemDto item : userCartItems) {
            itemDict.computeIfAbsent(item.getShopId(), k -> new ArrayList<>()).add(item);
        }
        List<UserCartDto> result = new ArrayList<>();
        itemDict.forEach((shopId, userCartItems1) -> result.add(new UserCartDto()
                .setShopId(shopId)
                .setShopName(shopNameDict.get(shopId))
                .setCartItems(userCartItems1)));
        return result;
    }

}
