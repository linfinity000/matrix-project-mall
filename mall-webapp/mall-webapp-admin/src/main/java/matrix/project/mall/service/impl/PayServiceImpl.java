package matrix.project.mall.service.impl;

import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Order;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderService orderService;

    @Override
    public void processOrderSuccess(List<String> orderIds) {
        List<Order> orderList = orderService.queryByOrderIds(orderIds);
        if (CollectionUtils.isEmpty(orderList)) {
            return;
        }
        orderList.forEach(item -> item.setStatus(Constant.ENABLED).setUpdateTime(new Date()));
        orderService.updateBatchById(orderList);
    }
}
