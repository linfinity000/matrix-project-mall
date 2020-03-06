package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.mapper.AtomsGoodsMapper;
import matrix.project.mall.service.AtomsGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class AtomsGoodsServiceImpl extends ServiceImpl<AtomsGoodsMapper, AtomsGoods> implements AtomsGoodsService {

    @Override
    public AtomsGoods queryById(String atomsGoodsId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATOMS_GOODS_ID", atomsGoodsId)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<AtomsGoods> queryByIds(List<String> atomsGoodsIds) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ATOMS_GOODS_ID", atomsGoodsIds)
                .eq("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

}
