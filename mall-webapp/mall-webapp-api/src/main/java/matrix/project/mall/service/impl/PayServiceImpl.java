package matrix.project.mall.service.impl;

import matrix.module.common.helper.Assert;
import matrix.module.pay.entity.MatrixPayEntity;
import matrix.module.pay.entity.MatrixRefundEntity;
import matrix.module.pay.enums.PayMode;
import matrix.module.pay.interfaces.PayInterface;
import matrix.module.pay.templates.AlipayTemplate;
import matrix.module.pay.vo.PayVo;
import matrix.project.mall.entity.Order;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Service
public class PayServiceImpl implements PayService, PayInterface {

    @Autowired
    private OrderService orderService;

    @Resource
    private AlipayTemplate alipayTemplate;

    @Override
    public boolean paySuccess(List<MatrixPayEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        List<String> orderIds = new ArrayList<>();
        for (MatrixPayEntity matrixPayEntity : list) {
            //todo 之后再做逻辑
            orderIds.add(matrixPayEntity.getOrderId());
        }
        orderService.processPayedOrderIds(orderIds);
        return true;
    }

    @Override
    public boolean refundSuccess(List<MatrixRefundEntity> list) {
        return false;
    }

    @Override
    public String getPayUrl(String orderId) {
        Order order = orderService.queryWaitPayOrderByOrderId(orderId);
        Assert.state(order != null, "订单查询为空");
        assert order != null;
        return alipayTemplate.doPay(PayMode.PC, new PayVo()
                .setTitle("订单号:" + order.getOrderId())
                .setDesc("订单号:" + order.getOrderId())
                .setOrderId(order.getOrderId())
                .setPrice(order.getPrice()));
    }
}
