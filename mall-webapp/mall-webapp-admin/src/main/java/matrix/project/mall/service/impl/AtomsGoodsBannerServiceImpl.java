package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.AtomsGoodsBanner;
import matrix.project.mall.mapper.AtomsGoodsBannerMapper;
import matrix.project.mall.service.AtomsGoodsBannerService;
import matrix.project.mall.vo.AtomsGoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class AtomsGoodsBannerServiceImpl extends ServiceImpl<AtomsGoodsBannerMapper, AtomsGoodsBanner> implements AtomsGoodsBannerService {

    @Override
    public List<AtomsGoodsBanner> queryByAtomsGoodsId(String atomsGoodsId) {
        QueryWrapper<AtomsGoodsBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATOMS_GOODS_ID", atomsGoodsId)
                .eq("STATUS", Constant.ENABLED)
                .orderByAsc("ORDER_BY");
        return list(queryWrapper);
    }

    @Override
    @Transactional
    public void saveAtomsGoodsBanner(String atomsGoodsId, List<AtomsGoodsVo.Banner> banners) {
        getBaseMapper().deleteByAtomsGoodsId(atomsGoodsId);
        if (CollectionUtils.isEmpty(banners)) {
            return;
        }
        List<AtomsGoodsBanner> atomsGoodsBanners = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < banners.size(); i++) {
            atomsGoodsBanners.add(new AtomsGoodsBanner()
                    .setId(RandomUtil.getUUID())
                    .setAtomsGoodsId(atomsGoodsId)
                    .setImageUrl(banners.get(i).getImageUrl())
                    .setOrderBy(i)
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setStatus(Constant.ENABLED));
        }
        saveBatch(atomsGoodsBanners);
    }

}
