package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Order;
import matrix.project.mall.vo.OrderVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
public interface OrderService extends IService<Order> {

    boolean saveOrder(OrderVo orderVo);

    Order queryWaitPayOrderByOrderId(String orderId);

    void processPayedOrderIds(List<String> orderIds);
}
