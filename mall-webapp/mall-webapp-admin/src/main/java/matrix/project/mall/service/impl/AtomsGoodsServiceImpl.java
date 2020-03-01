package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.AtomsGoodsDto;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.mapper.AtomsGoodsMapper;
import matrix.project.mall.service.*;
import matrix.project.mall.vo.AtomsGoodsVo;
import matrix.project.mall.vo.QueryAtomsGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private GoodsService goodsService;

    @Autowired
    private AtomsGoodsSkuLabelService atomsGoodsSkuLabelService;

    @Autowired
    private AtomsGoodsAttrLabelService atomsGoodsAttrLabelService;

    @Autowired
    private AtomsGoodsBannerService atomsGoodsBannerService;

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
        atomsGoodsDto.setBanners(atomsGoodsBannerService.queryByAtomsGoodsId(atomsGoodsId));
        return atomsGoodsDto;
    }

    @Override
    @Transactional
    public boolean saveAtomsGoods(AtomsGoodsVo atomsGoodsVo) {
        Assert.state(!StringUtils.isEmpty(atomsGoodsVo.getAtomsGoodsName()), "原子商品名称不允许为空");
        Assert.state(atomsGoodsVo.getBrandId() != null, "品牌不允许为空");
        Assert.state(atomsGoodsVo.getCategoryId() != null, "分类不允许为空");
        AtomsGoods atomsGoods;
        if (!StringUtils.isEmpty(atomsGoodsVo.getAtomsGoodsId())) {
            atomsGoods = queryByAtomsGoodsId(atomsGoodsVo.getAtomsGoodsId());
            Assert.state(atomsGoods != null, "未找到原子商品");
        } else {
            atomsGoods = new AtomsGoods()
                    .setAtomsGoodsId(RandomUtil.getUUID())
                    .setShopId(shopService.getShop().getShopId())
                    .setCreateTime(new Date());
        }
        assert atomsGoods != null;
        atomsGoods.setAtomsGoodsName(atomsGoodsVo.getAtomsGoodsName())
                .setAtomsGoodsImage(atomsGoodsVo.getAtomsGoodsImage())
                .setDescription(atomsGoodsVo.getDescription())
                .setSalePoints(atomsGoodsVo.getSalePoints())
                .setBrandId(atomsGoodsVo.getBrandId())
                .setCategoryId(atomsGoodsVo.getCategoryId())
                .setUpdateTime(new Date())
                .setStatus(atomsGoodsVo.getStatus());
        atomsGoodsBannerService.saveAtomsGoodsBanner(atomsGoods.getAtomsGoodsId(), atomsGoodsVo.getBanners());
        if (!StringUtils.isEmpty(atomsGoodsVo.getAtomsGoodsId())) {
            updateById(atomsGoods);
        } else {
            atomsGoodsSkuLabelService.saveLabel(atomsGoods.getAtomsGoodsId(), atomsGoodsVo.getSkuList());
            atomsGoodsAttrLabelService.saveLabel(atomsGoods.getAtomsGoodsId(), atomsGoodsVo.getAttrList());
            save(atomsGoods);
        }
        return true;
    }

    @Override
    public boolean removeAtomsGoods(String atomsGoodsId) {
        AtomsGoods atomsGoods = queryByAtomsGoodsId(atomsGoodsId);
        Assert.state(atomsGoods != null, "未找到原子商品");
        assert atomsGoods != null;
        Assert.state(goodsService.countByAtomsGoodsId(atomsGoodsId) <= 0, "原子商品下存在商品");
        atomsGoods.setStatus(Constant.DELETED);
        updateById(atomsGoods);
        return true;
    }

    @Override
    public AtomsGoods queryByAtomsGoodsId(String atomsGoodsId) {
        QueryWrapper<AtomsGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATOMS_GOODS_ID", atomsGoodsId)
                .ne("STATUS", Constant.DELETED);
        return getOne(queryWrapper, false);
    }

}
