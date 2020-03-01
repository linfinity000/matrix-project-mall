package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.mapper.GoodsMapper;
import matrix.project.mall.service.GoodsAttrService;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.service.GoodsSkuService;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GoodsDto getGoods(String goodsId) {
        GoodsDto goodsDto = getBaseMapper().getGoods(goodsId, shopService.getShop().getShopId());
        Assert.state(goodsDto != null, "商品不存在");
        assert goodsDto != null;
        goodsDto.setGoodsSkuList(goodsSkuService.queryByGoodsId(goodsId));
        goodsDto.setGoodsAttrList(goodsAttrService.queryByGoodsId(goodsId));
        return goodsDto;
    }

    @Override
    public boolean saveGoods(GoodsVo goodsVo) {
        return false;
    }

    @Override
    public boolean removeGoods(String goodsId) {
        Goods goods = queryByGoodsId(goodsId);
        Assert.state(goods != null, "未找到商品");
        assert goods != null;
        goods.setStatus(Constant.DELETED);
        updateById(goods);
        return true;
    }

    @Override
    public Goods queryByGoodsId(String goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId)
                .ne("STATUS", Constant.DELETED);
        return getOne(queryWrapper, false);
    }

}
