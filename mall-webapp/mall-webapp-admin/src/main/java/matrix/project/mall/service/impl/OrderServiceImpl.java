package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.project.mall.dto.ItemDto;
import matrix.project.mall.dto.OrderDto;
import matrix.project.mall.entity.Order;
import matrix.project.mall.entity.OrderExt;
import matrix.project.mall.entity.OrderGoods;
import matrix.project.mall.entity.Region;
import matrix.project.mall.enums.OrderStatus;
import matrix.project.mall.mapper.OrderMapper;
import matrix.project.mall.service.*;
import matrix.project.mall.vo.OrderAddressVo;
import matrix.project.mall.vo.QueryOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    private RegionService regionService;

    @Autowired
    private OrderExtService orderExtService;

    @Autowired
    private OrderGoodsService orderGoodsService;

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
                .setAddress(orderAddressVo.getAddress());
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

}
