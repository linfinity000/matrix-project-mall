package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.AtomsGoodsDto;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.mapper.AtomsGoodsMapper;
import matrix.project.mall.service.AtomsGoodsAttrLabelService;
import matrix.project.mall.service.AtomsGoodsService;
import matrix.project.mall.service.AtomsGoodsSkuLabelService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.AtomsGoodsVo;
import matrix.project.mall.vo.QueryAtomsGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class AtomsGoodsServiceImpl extends ServiceImpl<AtomsGoodsMapper, AtomsGoods> implements AtomsGoodsService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AtomsGoodsSkuLabelService atomsGoodsSkuLabelService;

    @Autowired
    private AtomsGoodsAttrLabelService atomsGoodsAttrLabelService;

    @Override
    public Integer countByShopId(String shopId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SHOP_ID", shopId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countByCategoryId(String categoryId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CATEGORY_ID", categoryId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countByBrandId(String brandId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BRAND_ID", brandId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countAtomsGoods(QueryAtomsGoodsVo queryAtomsGoodsVo) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryAtomsGoodsVo.getAtomsGoodsName())) {
            queryWrapper.like("ATOMS_GOODS_NAME", "%" + queryAtomsGoodsVo.getAtomsGoodsName() + "%");
        }
        queryWrapper.eq("SHOP_ID", shopService.getShop().getShopId());
        if (queryAtomsGoodsVo.getStatus() != null) {
            queryWrapper.eq("STATUS", queryAtomsGoodsVo.getStatus());
        } else {
            queryWrapper.ne("STATUS", Constant.DELETED);
        }
        return count(queryWrapper);
    }

    @Override
    public List<AtomsGoodsDto> listAtomsGoods(QueryAtomsGoodsVo queryAtomsGoodsVo) {
        IPage<AtomsGoods> page = new Page<>(queryAtomsGoodsVo.getPage(), queryAtomsGoodsVo.getPageSize());
        return getBaseMapper().listAtomsGoods(page, queryAtomsGoodsVo, shopService.getShop().getShopId());
    }

    @Override
    public AtomsGoodsDto getAtomsGoods(String atomsGoodsId) {
        AtomsGoodsDto atomsGoodsDto = getBaseMapper().getAtomsGoods(atomsGoodsId, shopService.getShop().getShopId());
        Assert.state(atomsGoodsDto != null, "原子商品不存在");
        assert atomsGoodsDto != null;
        atomsGoodsDto.setSkuList(atomsGoodsSkuLabelService.queryByAtomsGoodsId(atomsGoodsId));
        atomsGoodsDto.setAttrList(atomsGoodsAttrLabelService.queryByAtomsGoodsId(atomsGoodsId));
        return atomsGoodsDto;
    }

    @Override
    public boolean saveAtomsGoods(AtomsGoodsVo atomsGoodsVo) {
        return false;
    }

    @Override
    public boolean removeAtomsGoods(String atomsGoodsId) {
        return false;
    }

}
