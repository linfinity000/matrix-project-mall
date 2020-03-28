package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.OrderGoods;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
public interface OrderGoodsService extends IService<OrderGoods> {

    List<OrderGoods> listOrderGoods(String orderId);

    OrderGoods queryById(String orderGoodsId);

    List<OrderGoods> queryByOrderId(String orderId);
}
