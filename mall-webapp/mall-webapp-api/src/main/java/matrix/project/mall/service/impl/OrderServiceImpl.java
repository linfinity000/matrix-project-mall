package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.Order;
import matrix.project.mall.mapper.OrderMapper;
import matrix.project.mall.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
