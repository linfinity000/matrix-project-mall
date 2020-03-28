package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.OrderPayGroup;
import matrix.project.mall.mapper.OrderPayGroupMapper;
import matrix.project.mall.service.OrderPayGroupService;
import matrix.project.mall.utils.LoginUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Service
public class OrderPayGroupServiceImpl extends ServiceImpl<OrderPayGroupMapper, OrderPayGroup> implements OrderPayGroupService {

    @Override
    public String savePayGroup(List<String> orderIds, List<String> shopIds, BigDecimal price, String payMode, Integer payChannel) {
        Date date = new Date();
        OrderPayGroup payGroup = new OrderPayGroup()
                .setId(RandomUtil.getUUID())
                .setOrderIds(String.join(",", orderIds))
                .setShopIds(String.join(",", shopIds))
                .setPrice(price)
                .setPayMode(payMode)
                .setPayChannel(payChannel)
                .setUserId(LoginUtil.getUser().getUserId())
                .setCreateTime(date)
                .setUpdateTime(date)
                .setStatus(Constant.DISABLED);
        save(payGroup);
        return payGroup.getId();
    }

    @Override
    public List<OrderPayGroup> queryByWaitPayGroupIds(List<String> payGroupIds) {
        QueryWrapper<OrderPayGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ID", payGroupIds)
                .ne("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

}
