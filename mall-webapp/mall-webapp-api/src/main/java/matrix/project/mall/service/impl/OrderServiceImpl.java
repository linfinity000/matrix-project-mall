package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.based.utils.JacksonUtil;
import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.entity.*;
import matrix.project.mall.enums.Logistics;
import matrix.project.mall.enums.OrderStatus;
import matrix.project.mall.mapper.OrderMapper;
import matrix.project.mall.service.*;
import matrix.project.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderExtService orderExtService;

    @Autowired
    private AddressService addressService;

    @Override
    @Transactional
    public boolean saveOrder(OrderVo orderVo) {
        Assert.state(!CollectionUtils.isEmpty(orderVo.getCartIds()), "购物车IDS不允许为空");
        //查询购物车信息
        List<UserCart> userCarts = userCartService.queryByIds(orderVo.getCartIds());
        Assert.state(!CollectionUtils.isEmpty(userCarts), "无有效的购物车");
        Map<String, UserCart> userCartMap = userCarts.stream().collect(Collectors.toMap(UserCart::getGoodsId, item -> item, (o1, o2) -> o2));
        //查询商品信息
        List<Goods> goods = goodsService.queryByGoodsIds(userCarts.stream().map(UserCart::getGoodsId).collect(Collectors.toList()));
        Map<String, Goods> goodsMap = goods.stream().collect(Collectors.toMap(Goods::getGoodsId, item -> item, (o1, o2) -> o2));
        //查询原子商品信息
        List<AtomsGoods> atomsGoods = atomsGoodsService.queryByIds(goods.stream().map(Goods::getAtomsGoodsId).distinct().collect(Collectors.toList()));
        Map<String, AtomsGoods> atomsGoodsMap = atomsGoods.stream().collect(Collectors.toMap(AtomsGoods::getAtomsGoodsId, item -> item, (o1, o2) -> o2));
        Map<String, List<String>> goodsIdsMap = new HashMap<>();
        for (UserCart userCart : userCarts) {
            goodsIdsMap.computeIfAbsent(userCart.getShopId(), k -> new ArrayList<>()).add(userCart.getGoodsId());
        }
        List<Order> orders = new ArrayList<>();
        List<OrderGoods> orderGoods = new ArrayList<>();
        List<OrderExt> orderExts = new ArrayList<>();
        // 查询收货地址
        final Address address = !StringUtils.isEmpty(orderVo.getAddressId())
                ? addressService.queryById(orderVo.getAddressId())
                : null;
        goodsIdsMap.forEach((shopId, goodsIds) -> {
            String orderId = RandomUtil.getUUID();
            Date date = new Date();
            BigDecimal price = BigDecimal.ZERO;
            boolean hasLogistics = false;
            //生成订单商品
            for (String goodsId : goodsIds) {
                UserCart userCart = userCartMap.get(goodsId);
                AtomsGoods tempAtomsGoods = atomsGoodsMap.get(goodsId);
                Goods tempGoods = goodsMap.get(goodsId);
                if (tempGoods.getStock() < userCart.getGoodsCount()) {
                    throw new ServiceException(String.format("商品%s库存不足", tempAtomsGoods.getAtomsGoodsName()));
                }
                Map<String, Object> mirror = new HashMap<>();
                mirror.put("atomsGoods", tempAtomsGoods);
                mirror.put("goods", tempGoods);
                BigDecimal goodsTotalPrice = tempGoods.getSalePrice().multiply(new BigDecimal(userCart.getGoodsCount()));
                orderGoods.add(new OrderGoods()
                        .setId(RandomUtil.getUUID())
                        .setOrderId(orderId)
                        .setGoodsId(goodsId)
                        .setGoodsCount(userCart.getGoodsCount())
                        .setGoodsTotalPrice(goodsTotalPrice)
                        .setMirror(JacksonUtil.toJsonString(mirror)));
                price = price.add(goodsTotalPrice);
                hasLogistics = tempAtomsGoods.getHasLogistics().equals(Logistics.HAS_LOGISTICS.getCode());
                tempGoods.setStock(tempGoods.getStock() - userCart.getGoodsCount())
                        .setUpdateTime(date);
            }
            //生成订单扩展表数据
            OrderExt orderExt = new OrderExt()
                    .setId(RandomUtil.getUUID())
                    .setOrderId(orderId)
                    .setRemark(orderVo.getRemark());
            if (hasLogistics) {
                //如果有物流
                if (address == null) {
                    throw new ServiceException("请选择收货地址");
                }
                orderExt.setProvinceCode(address.getProvinceCode())
                        .setProvinceName(address.getProvinceName())
                        .setCityCode(address.getCityCode())
                        .setCityName(address.getCityName())
                        .setAreaCode(address.getAreaCode())
                        .setAreaName(address.getAreaName())
                        .setAddress(address.getAddress());
            }
            orderExts.add(orderExt);
            //生成订单
            orders.add(new Order()
                    .setOrderId(orderId)
                    .setShopId(shopId)
                    .setPrice(price)
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setStatus(OrderStatus.WAIT_PAYING.getCode()));
        });
        goodsService.updateBatchById(goods);
        saveBatch(orders);
        orderGoodsService.saveBatch(orderGoods);
        orderExtService.saveBatch(orderExts);
        return true;
    }
}
