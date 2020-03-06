package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.OrderGoods;
import matrix.project.mall.mapper.OrderGoodsMapper;
import matrix.project.mall.service.OrderGoodsService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {
}
