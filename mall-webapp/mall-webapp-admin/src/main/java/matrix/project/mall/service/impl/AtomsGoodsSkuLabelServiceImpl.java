package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoodsSkuLabel;
import matrix.project.mall.mapper.AtomsGoodsSkuLabelMapper;
import matrix.project.mall.service.AtomsGoodsSkuLabelService;
import matrix.project.mall.vo.AtomsGoodsVo;
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
public class AtomsGoodsSkuLabelServiceImpl extends ServiceImpl<AtomsGoodsSkuLabelMapper, AtomsGoodsSkuLabel> implements AtomsGoodsSkuLabelService {

    @Override
    public List<AtomsGoodsSkuLabel> queryByAtomsGoodsId(String atomsGoodsId) {
        QueryWrapper<AtomsGoodsSkuLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("ATOMS_GOODS_ID", atomsGoodsId);
        return list(queryWrapper);
    }

    @Override
    public void saveLabel(String atomsGoodsId, List<AtomsGoodsVo.SkuLabel> skuList) {
        if (CollectionUtils.isEmpty(skuList)) {
            return;
        }
        List<AtomsGoodsSkuLabel> labels = new ArrayList<>();
        Date time = new Date();
        skuList.forEach(skuLabel -> labels.add(new AtomsGoodsSkuLabel()
                .setId(RandomUtil.getUUID())
                .setAtomsGoodsId(atomsGoodsId)
                .setSkuName(skuLabel.getSkuName())
                .setCreateTime(time)
                .setUpdateTime(time)
                .setStatus(Constant.ENABLED)
        ));
        saveBatch(labels);
    }

}
