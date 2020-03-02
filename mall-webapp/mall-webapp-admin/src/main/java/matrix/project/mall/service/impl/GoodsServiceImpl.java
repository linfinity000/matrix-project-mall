package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.converter.SkuLabelConvert;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.dto.SkuDto;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.mapper.GoodsMapper;
import matrix.project.mall.service.*;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;
import matrix.project.mall.vo.QuerySkuLabelVo;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private GoodsAttrService goodsAttrService;

    @Override
    public Integer countByAtomsGoodsId(String atomsGoodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATOMS_GOODS_ID", atomsGoodsId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }

    @Override
    public Integer countGoods(QueryGoodsVo queryGoodsVo) {
        return getBaseMapper().countGoods(shopService.getShop().getShopId(), queryGoodsVo);
    }

    @Override
    public List<GoodsDto> listGoods(QueryGoodsVo queryGoodsVo) {
        IPage<Goods> page = new Page<>(queryGoodsVo.getPage(), queryGoodsVo.getPageSize());
        return getBaseMapper().listGoods(page, shopService.getShop().getShopId(), queryGoodsVo);
    }

    @Override
    public String queryGoodsIdBySkuLabel(List<QuerySkuLabelVo> list) {
        return getBaseMapper().queryGoodsIdBySkuLabel(list, list.size());
    }

    @Override
    public GoodsDto getGoods(String goodsId) {
        GoodsDto goodsDto = getBaseMapper().getGoods(goodsId, shopService.getShop().getShopId());
        Assert.state(goodsDto != null, "商品不存在");
        return goodsDto;
    }

    @Override
    @Transactional
    public boolean saveGoods(GoodsVo goodsVo) {
        Assert.state(!StringUtils.isEmpty(goodsVo.getAtomsGoodsId()), "原子商品ID不允许为空");
        Assert.state(goodsVo.getSalePrice() != null, "销售价不允许为空");
        Assert.state(goodsVo.getStock() != null, "库存不允许为空");
        Assert.state(goodsVo.getStatus() != null, "状态不允许为空");
        atomsGoodsService.verifyHasAtomsGoodsId(goodsVo.getAtomsGoodsId());
        Goods goods;
        if (!StringUtils.isEmpty(goodsVo.getGoodsId())) {
            goods = queryByGoodsId(goodsVo.getGoodsId());
            Assert.state(goods != null, "查询商品为空");
        } else {
            goods = new Goods()
                    .setGoodsId(RandomUtil.getUUID())
                    .setAtomsGoodsId(goodsVo.getAtomsGoodsId())
                    .setCreateTime(new Date());
        }
        assert goods != null;
        goods.setOriginPrice(goodsVo.getOriginPrice())
                .setSalePrice(goodsVo.getSalePrice())
                .setStock(goodsVo.getStock())
                .setImageUrl(goodsVo.getImageUrl())
                .setUpdateTime(new Date())
                .setStatus(goodsVo.getStatus());
        if (!StringUtils.isEmpty(goodsVo.getGoodsId())) {
            updateById(goods);
        } else {
            goodsSkuService.saveGoodsSku(goods.getGoodsId(), goodsVo.getSkuLabels());
            save(goods);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeGoods(String goodsId) {
        Goods goods = queryByGoodsId(goodsId);
        Assert.state(goods != null, "未找到商品");
        assert goods != null;
        atomsGoodsService.verifyHasAtomsGoodsId(goods.getAtomsGoodsId());
        goods.setStatus(Constant.DELETED);
        updateById(goods);
        goodsSkuService.removeSku(goodsId);
        goodsAttrService.removeAttr(goodsId);
        return true;
    }

    @Override
    public Goods queryByGoodsId(String goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId)
                .ne("STATUS", Constant.DELETED);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<SkuDto> skuLabels(String atomsGoodsId) {
        return SkuLabelConvert.convert(getBaseMapper().skuLabels(atomsGoodsId));
    }

    @Override
    public GoodsDto getGoods(List<QuerySkuLabelVo> querySkuLabelVos) {
        String goodsId = queryGoodsIdBySkuLabel(querySkuLabelVos);
        if (StringUtils.isEmpty(goodsId)) {
            return new GoodsDto();
        }
        return getGoods(goodsId);
    }

    @Override
    public GoodsDto getGoodsByAtomsGoodsId(String atomsGoodsId) {
        atomsGoodsService.verifyHasAtomsGoodsId(atomsGoodsId);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATOMS_GOODS_ID", atomsGoodsId)
                .ne("STATUS", Constant.DELETED);
        Goods goods = getOne(queryWrapper, false);
        if (goods == null) {
            return new GoodsDto().setAtomsGoodsId(atomsGoodsId);
        }
        return getGoods(goods.getGoodsId());
    }

}
