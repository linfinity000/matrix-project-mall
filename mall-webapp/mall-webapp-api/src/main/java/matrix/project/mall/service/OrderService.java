package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.ApiOrderDto;
import matrix.project.mall.dto.PageDto;
import matrix.project.mall.entity.Order;
import matrix.project.mall.vo.OrderVo;
import matrix.project.mall.vo.QueryOrderListVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
public interface OrderService extends IService<Order> {

    boolean saveOrder(OrderVo orderVo);

    Order queryWaitPayOrderByOrderId(String orderId);

    List<Order> queryWaitPayOrderByOrderIds(List<String> orderIds);

    void processPayedOrderIds(List<String> orderIds);

    boolean cancelOrder(String orderId);

    Order queryByOrderId(String orderId);

    Integer countOrder();

    PageDto<ApiOrderDto> orderList(QueryOrderListVo queryOrderListVo);
}
