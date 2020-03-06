package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Order;
import matrix.project.mall.vo.OrderVo;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
public interface OrderService extends IService<Order> {

    boolean saveOrder(OrderVo orderVo);

}
