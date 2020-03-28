package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.pay.entity.MatrixPayEntity;
import matrix.module.pay.templates.AlipayTemplate;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.OrderPayGroup;
import matrix.project.mall.enums.PayChannel;
import matrix.project.mall.mapper.OrderPayGroupMapper;
import matrix.project.mall.service.OrderPayGroupService;
import matrix.project.mall.service.PayService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.QueryPayGroupVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Service
public class OrderPayGroupServiceImpl extends ServiceImpl<OrderPayGroupMapper, OrderPayGroup> implements OrderPayGroupService {

    private static final Logger logger = LogManager.getLogger(OrderPayGroupServiceImpl.class);

    @Autowired
    private ShopService shopService;

    @Autowired
    private PayService payService;

    @Resource
    private AlipayTemplate alipayTemplate;

//    @Resource
//    private WepayTemplate wepayTemplate;

    @Override
    public Integer countPayGroup(QueryPayGroupVo payGroupVo) {
        QueryWrapper<OrderPayGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("SHOP_IDS", "%" + shopService.getShop().getShopId() + "%")
                .ne("STATUS", Constant.DELETED);
        if (!StringUtils.isEmpty(payGroupVo.getOrderId())) {
            queryWrapper.like("ORDER_IDS", "%" + payGroupVo.getOrderId() + "%");
        }
        if (payGroupVo.getStatus() != null) {
            queryWrapper.eq("STATUS", payGroupVo.getStatus());
        }
        return count(queryWrapper);
    }

    @Override
    public List<OrderPayGroup> listPayGroup(QueryPayGroupVo payGroupVo) {
        QueryWrapper<OrderPayGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("SHOP_IDS", "%" + shopService.getShop().getShopId() + "%")
                .ne("STATUS", Constant.DELETED);
        if (!StringUtils.isEmpty(payGroupVo.getOrderId())) {
            queryWrapper.like("ORDER_IDS", "%" + payGroupVo.getOrderId() + "%");
        }
        if (payGroupVo.getStatus() != null) {
            queryWrapper.eq("STATUS", payGroupVo.getStatus());
        }
        queryWrapper.orderByDesc("UPDATE_TIME");
        IPage<OrderPayGroup> page = new Page<>(payGroupVo.getPage(), payGroupVo.getPageSize());
        return page(page, queryWrapper).getRecords();
    }

    @Override
    public OrderPayGroup queryById(String id) {
        QueryWrapper<OrderPayGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", id);
        return getOne(queryWrapper, false);
    }

    @Override
    @Transactional
    public boolean checkOrderStatus(String payGroupId) {
        OrderPayGroup orderPayGroup = queryById(payGroupId);
        Assert.state(orderPayGroup != null, "未找到支付订单");
        assert orderPayGroup != null;
        Assert.state(!Constant.ENABLED.equals(orderPayGroup.getStatus()), "该订单已经支付");
        List<MatrixPayEntity> payEntities = null;
        if (PayChannel.ALI.getCode().equals(orderPayGroup.getPayChannel())) {
            payEntities = alipayTemplate.doQueryPayByOrderId(payGroupId);
        }
//        else if (PayChannel.WE.getCode().equals(orderPayGroup.getPayChannel())) {
//            payEntities = wepayTemplate.doQueryPayByOrderId(payGroupId);
//        }
        Assert.state(!CollectionUtils.isEmpty(payEntities), "该订单未支付");
        assert payEntities != null;
        if (payEntities.size() >= 1) {
            logger.warn(String.format("支付订单号:%s, 有%d次支付记录", payGroupId, payEntities.size()));
        }
        orderPayGroup.setStatus(Constant.ENABLED);
        payService.processOrderSuccess(Arrays.asList(orderPayGroup.getOrderIds().split(",")));
        updateById(orderPayGroup);
        return true;
    }
}
