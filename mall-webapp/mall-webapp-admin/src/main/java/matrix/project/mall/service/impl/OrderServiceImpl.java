package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.project.mall.dto.ItemDto;
import matrix.project.mall.dto.OrderDto;
import matrix.project.mall.entity.*;
import matrix.project.mall.enums.Logistics;
import matrix.project.mall.enums.OrderStatus;
import matrix.project.mall.mapper.OrderMapper;
import matrix.project.mall.service.*;
import matrix.project.mall.vo.OrderAddressVo;
import matrix.project.mall.vo.QueryOrderVo;
import matrix.project.mall.vo.ShipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private OrderExtService orderExtService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private LogisticsService logisticsService;

    @Override
    public List<ItemDto> orderStatus() {
        List<ItemDto> result = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            result.add(new ItemDto()
                    .setCode(orderStatus.getCode())
                    .setName(orderStatus.getName()));
        }
        return result;
    }

    @Override
    public Integer countOrder(QueryOrderVo queryOrderVo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (queryOrderVo.getOrderStatus() != null) {
            queryWrapper.eq("ORDER_STATUS", queryOrderVo.getOrderStatus());
        }
        queryWrapper.eq("SHOP_ID", shopService.getShop().getShopId());
        return count(queryWrapper);
    }

    @Override
    public List<OrderDto> listOrder(QueryOrderVo queryOrderVo) {
        IPage<Order> page = new Page<>(queryOrderVo.getPage(), queryOrderVo.getPageSize());
        return getBaseMapper().listOrder(page, queryOrderVo, shopService.getShop().getShopId());
    }

    @Override
    public boolean saveOrderAddress(OrderAddressVo orderAddressVo) {
        Assert.state(!StringUtils.isEmpty(orderAddressVo.getOrderId()), "订单号不能为空");
        Assert.state(!StringUtils.isEmpty(orderAddressVo.getLinkName()), "联系人不能为空");
        Assert.state(!StringUtils.isEmpty(orderAddressVo.getMobile()), "手机号不能为空");
        Assert.state(!StringUtils.isEmpty(orderAddressVo.getAddress()), "详细地址不能为空");
        Assert.state(orderAddressVo.getRegions().size() == 3, "地区选择只能是3级");
        List<Region> regions = regionService.queryByCodes(orderAddressVo.getRegions());
        Map<Long, Region> regionMap = regions.stream().collect(Collectors.toMap(Region::getCode, item -> item, (o1, o2) -> o2));
        Order order = queryByOrderId(orderAddressVo.getOrderId());
        Assert.state(order != null, "订单不存在");
        assert order != null;
        //待支付和待发货可以修改收货地址
        if (!(order.getOrderStatus().equals(OrderStatus.WAIT_PAYING.getCode()) || order.getOrderStatus().equals(OrderStatus.WAIT_SHIPPING.getCode()))) {
            throw new ServiceException("订单状态已流转，无法修改地址");
        }
        OrderExt orderExt = orderExtService.queryByOrderId(orderAddressVo.getOrderId());
        Assert.state(orderExt != null, "订单扩展表查询为空");
        assert orderExt != null;
        Region province = regionMap.get(orderAddressVo.getRegions().get(0));
        Region city = regionMap.get(orderAddressVo.getRegions().get(1));
        Region area = regionMap.get(orderAddressVo.getRegions().get(2));
        orderExt.setProvinceCode(province.getCode())
                .setProvinceName(province.getName())
                .setCityCode(city.getCode())
                .setCityName(city.getName())
                .setAreaCode(area.getCode())
                .setAreaName(area.getName())
                .setAddress(orderAddressVo.getAddress())
                .setLinkName(orderAddressVo.getLinkName())
                .setMobile(orderAddressVo.getMobile());
        orderExtService.updateById(orderExt);
        return true;
    }

    @Override
    public Order queryByOrderId(String orderId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<OrderGoods> listOrderGoods(String orderId) {
        return orderGoodsService.listOrderGoods(orderId);
    }

    @Override
    @Transactional
    public boolean saveShip(ShipVo shipVo) {
        Assert.state(!StringUtils.isEmpty(shipVo.getOrderGoodsId()), "订单商品ID不允许为空");
        OrderGoods orderGoods = orderGoodsService.queryById(shipVo.getOrderGoodsId());
        Assert.state(orderGoods != null, "订单商品未找到");
        assert orderGoods != null;
        Order order = queryByOrderId(orderGoods.getOrderId());
        Assert.state(order != null, "订单未找到");
        assert order != null;
        Assert.state(order.getOrderStatus().equals(OrderStatus.WAIT_SHIPPING.getCode())
                || order.getOrderStatus().equals(OrderStatus.PART_SHIP.getCode()), "订单状态不支持发货");
        List<OrderGoods> orderGoodsList = orderGoodsService.listOrderGoods(order.getOrderId());
        boolean isPartShip = false;
        for (OrderGoods tempOrderGoods : orderGoodsList) {
            boolean hasLogisticsNoShip = tempOrderGoods.getHasLogistics().equals(Logistics.HAS_LOGISTICS.getCode()) && StringUtils.isEmpty(tempOrderGoods.getLogisticsNo());
            boolean hasNoLogisticsNoShip = tempOrderGoods.getHasLogistics().equals(Logistics.HAS_NO_LOGISTICS.getCode()) && StringUtils.isEmpty(tempOrderGoods.getGoodsSecret());
            if ((hasLogisticsNoShip || hasNoLogisticsNoShip)
                    && !tempOrderGoods.getId().equals(shipVo.getOrderGoodsId())) {
                isPartShip = true;
                break;
            }
        }
        //更新订单状态
        order.setOrderStatus(isPartShip ? OrderStatus.PART_SHIP.getCode() : OrderStatus.SHIPPED.getCode())
                .setUpdateTime(new Date());
        updateById(order);
        //更新商品物流信息
        if (orderGoods.getHasLogistics().equals(Logistics.HAS_LOGISTICS.getCode())) {
            //有物流
            Assert.state(!StringUtils.isEmpty(shipVo.getLogisticsCompanyId()), "快递公司不允许为空");
            Assert.state(!StringUtils.isEmpty(shipVo.getLogisticsNo()), "运单号不允许为空");
            orderGoods.setLogisticsCompanyId(shipVo.getLogisticsCompanyId())
                    .setLogisticsCompanyName(logisticsService.queryByLogisticsId(shipVo.getLogisticsCompanyId()).getLogisticsName())
                    .setLogisticsNo(shipVo.getLogisticsNo());
        } else {
            //无物流
            Assert.state(!StringUtils.isEmpty(shipVo.getGoodsSecret()), "商品密钥不允许为空");
            orderGoods.setGoodsSecret(shipVo.getGoodsSecret());
        }
        orderGoodsService.updateById(orderGoods);
        return true;
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderId) {
        Order order = queryByOrderId(orderId);
        Assert.state(order != null, "订单未找到");
        assert order != null;
        if (order.getOrderStatus().equals(OrderStatus.WAIT_PAYING.getCode())) {
            order.setOrderStatus(OrderStatus.CANCEL_ORDER.getCode())
                    .setUpdateTime(new Date());
            List<OrderGoods> orderGoodsList = orderGoodsService.queryByOrderId(order.getOrderId());
            Map<String, Integer> countMap = orderGoodsList.stream().collect(Collectors.toMap(OrderGoods::getGoodsId, OrderGoods::getGoodsCount, (o1, o2) -> o2));
            List<String> goodsIds = orderGoodsList.stream().map(OrderGoods::getGoodsId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(goodsIds)) {
                List<Goods> goodsList = goodsService.queryByGoodsIds(goodsIds);
                for (Goods goods : goodsList) {
                    goods.setStock(goods.getStock() + countMap.get(goods.getGoodsId()))
                            .setUpdateTime(new Date());
                }
                goodsService.updateBatchById(goodsList);
            }
            updateById(order);
            return true;
        }
        throw new ServiceException("只能取消待支付订单，其他订单请走申请退款");
    }

    @Override
    public List<Order> queryByOrderIds(List<String> orderIds) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ORDER_ID", orderIds);
        return list(queryWrapper);
    }

}
