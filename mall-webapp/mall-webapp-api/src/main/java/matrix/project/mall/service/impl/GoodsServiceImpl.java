package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.mapper.GoodsMapper;
import matrix.project.mall.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public Goods queryByGoodsId(String goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId)
                .eq("STATUS", Constant.ENABLED);
        Goods goods = getOne(queryWrapper, false);
        Assert.state(goods != null, "查询商品为空");
        return goods;
    }

}
