package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoodsAttrLabel;
import matrix.project.mall.mapper.AtomsGoodsAttrLabelMapper;
import matrix.project.mall.service.AtomsGoodsAttrLabelService;
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
public class AtomsGoodsAttrLabelServiceImpl extends ServiceImpl<AtomsGoodsAttrLabelMapper, AtomsGoodsAttrLabel> implements AtomsGoodsAttrLabelService {
    @Override
    public List<AtomsGoodsAttrLabel> queryByAtomsGoodsId(String atomsGoodsId) {
        QueryWrapper<AtomsGoodsAttrLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("ATOMS_GOODS_ID", atomsGoodsId);
        return list(queryWrapper);
    }

    @Override
    public void saveLabel(String atomsGoodsId, List<AtomsGoodsVo.AttrLabel> attrList) {
        if (CollectionUtils.isEmpty(attrList)) {
            return;
        }
        List<AtomsGoodsAttrLabel> labels = new ArrayList<>();
        Date time = new Date();
        for (int i = 0; i < attrList.size(); i++) {
            labels.add(new AtomsGoodsAttrLabel()
                    .setId(RandomUtil.getUUID())
                    .setAtomsGoodsId(atomsGoodsId)
                    .setAttrName(attrList.get(i).getAttrName())
                    .setOrderBy(i)
                    .setCreateTime(time)
                    .setUpdateTime(time)
                    .setStatus(Constant.ENABLED));
        }
        saveBatch(labels);
    }
}
