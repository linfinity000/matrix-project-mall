package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.GoodsAttr;
import matrix.project.mall.mapper.GoodsAttrMapper;
import matrix.project.mall.service.GoodsAttrService;
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
public class GoodsAttrServiceImpl extends ServiceImpl<GoodsAttrMapper, GoodsAttr> implements GoodsAttrService {

    @Override
    public void removeAttr(String goodsId) {
        getBaseMapper().removeAttr(goodsId);
    }

    @Override
    public void saveGoodsAttr(String goodsId, List<GoodsVo.AttrLabel> attrLabels) {
        if (CollectionUtils.isEmpty(attrLabels)) {
            return;
        }
        removeAttr(goodsId);
        List<GoodsAttr> list = new ArrayList<>();
        Date date = new Date();
        attrLabels.forEach(attrLabel -> list.add(new GoodsAttr()
                .setId(RandomUtil.getUUID())
                .setGoodsId(goodsId)
                .setAtomsGoodsAttrLabelId(attrLabel.getLabelId())
                .setAttrValue(attrLabel.getAttrValue())
                .setCreateTime(date)
                .setUpdateTime(date)
                .setStatus(Constant.ENABLED)));
        saveBatch(list);
    }
}
