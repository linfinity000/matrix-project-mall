package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoodsAttrLabel;
import matrix.project.mall.mapper.AtomsGoodsAttrLabelMapper;
import matrix.project.mall.service.AtomsGoodsAttrLabelService;
import org.springframework.stereotype.Service;

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
}
