package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.OrderExt;
import matrix.project.mall.mapper.OrderExtMapper;
import matrix.project.mall.service.OrderExtService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderExtServiceImpl extends ServiceImpl<OrderExtMapper, OrderExt> implements OrderExtService {

    @Override
    public OrderExt queryByOrderId(String orderId) {
        QueryWrapper<OrderExt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId);
        return getOne(queryWrapper, false);
    }

}
