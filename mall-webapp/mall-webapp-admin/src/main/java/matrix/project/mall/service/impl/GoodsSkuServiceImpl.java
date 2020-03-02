package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.GoodsSku;
import matrix.project.mall.mapper.GoodsSkuMapper;
import matrix.project.mall.service.GoodsSkuService;
import matrix.project.mall.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

    @Override
    public void saveGoodsSku(String goodsId, List<GoodsVo.SkuLabel> skuLabels) {
        if (CollectionUtils.isEmpty(skuLabels)) {
            return;
        }
        List<GoodsSku> goodsSkuList = new ArrayList<>();
        Date date = new Date();
        skuLabels.forEach(skuLabel -> goodsSkuList.add(new GoodsSku()
                .setId(RandomUtil.getUUID())
                .setGoodsId(goodsId)
                .setAtomsGoodsSkuLabelId(skuLabel.getLabelId())
                .setSkuValue(skuLabel.getSkuValue())
                .setCreateTime(date)
                .setUpdateTime(date)
                .setStatus(Constant.ENABLED)));
        saveBatch(goodsSkuList);
    }

    @Override
    public void removeSku(String goodsId) {
        getBaseMapper().removeSku(goodsId);
    }

}
