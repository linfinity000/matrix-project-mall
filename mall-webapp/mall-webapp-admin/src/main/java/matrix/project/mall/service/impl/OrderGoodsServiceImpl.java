package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.OrderGoods;
import matrix.project.mall.mapper.OrderGoodsMapper;
import matrix.project.mall.service.OrderGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {

    @Override
    public List<OrderGoods> listOrderGoods(String orderId) {
        QueryWrapper<OrderGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId)
                .orderByAsc("ID");
        return list(queryWrapper);
    }

}
