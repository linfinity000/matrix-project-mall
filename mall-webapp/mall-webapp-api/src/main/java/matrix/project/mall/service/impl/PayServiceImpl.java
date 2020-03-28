package matrix.project.mall.service.impl;

import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.module.pay.entity.MatrixPayEntity;
import matrix.module.pay.entity.MatrixRefundEntity;
import matrix.module.pay.enums.PayMode;
import matrix.module.pay.interfaces.PayInterface;
import matrix.module.pay.templates.AlipayTemplate;
import matrix.module.pay.vo.PayVo;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Order;
import matrix.project.mall.entity.OrderPayGroup;
import matrix.project.mall.enums.PayChannel;
import matrix.project.mall.service.OrderPayGroupService;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.service.PayService;
import matrix.project.mall.vo.DoPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Service
public class PayServiceImpl implements PayService, PayInterface {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderPayGroupService orderPayGroupService;

    @Resource
    private AlipayTemplate alipayTemplate;

//    @Resource
//    private WepayTemplate wepayTemplate;

    @Override
    public boolean paySuccess(List<MatrixPayEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        List<String> payGroupIds = new ArrayList<>();
        for (MatrixPayEntity matrixPayEntity : list) {
            payGroupIds.add(matrixPayEntity.getOrderId());
        }
        List<OrderPayGroup> payGroups = orderPayGroupService.queryByWaitPayGroupIds(payGroupIds);
        if (!CollectionUtils.isEmpty(payGroups)) {
            List<String> orderIds = new ArrayList<>();
            for (OrderPayGroup payGroup : payGroups) {
                payGroup.setStatus(Constant.ENABLED);
                orderIds.addAll(Arrays.asList(payGroup.getOrderIds().split(",")));
            }
            orderPayGroupService.updateBatchById(payGroups);
            orderService.processPayedOrderIds(orderIds);
        }
        return true;
    }

    @Override
    public boolean refundSuccess(List<MatrixRefundEntity> list) {
        //这里是退款单逻辑
        return false;
    }

    @Override
    @Transactional
    public String getPayUrl(DoPayVo payVo) {
        if (CollectionUtils.isEmpty(payVo.getOrderIds())) {
            throw new ServiceException("订单号不允许为空");
        }
        List<Order> orders = orderService.queryWaitPayOrderByOrderIds(payVo.getOrderIds());
        Assert.state(!CollectionUtils.isEmpty(orders), "订单查询为空");
        List<String> orderIds = orders.stream().map(Order::getOrderId).collect(Collectors.toList());
        List<String> shopIds = orders.stream().map(Order::getShopId).distinct().collect(Collectors.toList());
        BigDecimal price = BigDecimal.ZERO;
        for (Order order : orders) {
            price = price.add(order.getPrice());
        }
        if (price.compareTo(BigDecimal.ZERO) == 0) {
            throw new ServiceException("订单金额为0");
        }
        String payGroupId = orderPayGroupService.savePayGroup(orderIds, shopIds,
                price, payVo.getPayMode(), payVo.getPayChannel());
        if (PayChannel.ALI.getCode().equals(payVo.getPayChannel())) {
            return alipayTemplate.doPay(PayMode.getByCode(payVo.getPayMode()), new PayVo()
                    .setTitle("支付订单号:" + payGroupId)
                    .setDesc("订单号:" + String.join(",", orderIds))
                    .setOrderId(payGroupId)
                    .setPrice(price));
        }
//        else if (PayChannel.WE.getCode().equals(payVo.getPayChannel())) {
//            return wepayTemplate.doPay(PayMode.getByCode(payVo.getPayMode()), new PayVo()
//                    .setTitle("支付订单号:" + payGroupId)
//                    .setDesc("订单号:" + String.join(",", orderIds))
//                    .setOrderId(payGroupId)
//                    .setPrice(price));
//        }
        throw new ServiceException("支付渠道不存在");
    }
}
