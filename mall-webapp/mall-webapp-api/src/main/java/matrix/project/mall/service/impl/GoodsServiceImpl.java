package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.CategoryGoodsDto;
import matrix.project.mall.dto.GoodsNameDto;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.mapper.GoodsMapper;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.utils.LoginUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public Goods queryByGoodsId(String goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId)
                .eq("STATUS", Constant.ENABLED);
        Goods goods = getOne(queryWrapper, false);
        Assert.state(goods != null, "查询商品为空");
        return goods;
    }

    @Override
    public List<Goods> queryByGoodsIds(List<String> goodsIds) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("GOODS_ID", goodsIds)
                .eq("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

    @Override
    public List<GoodsNameDto> goodsNameList(List<String> goodsIds) {
        return getBaseMapper().goodsNameList(goodsIds);
    }

    @Override
    public List<CategoryGoodsDto> listGoods(String shopId) {
        List<CategoryGoodsDto.GoodsItemDto> goodsItems;
        if (LoginUtil.getUser() != null) {
            goodsItems = getBaseMapper().listGoodsItemsByUserId(shopId, LoginUtil.getUser().getUserId());
        } else {
            goodsItems = getBaseMapper().listGoodsItems(shopId);
        }
        if (CollectionUtils.isEmpty(goodsItems)) {
            return new ArrayList<>();
        }
        Map<String, String> categoryNameDict = goodsItems.stream().collect(Collectors.toMap(CategoryGoodsDto.GoodsItemDto::getCategoryId, CategoryGoodsDto.GoodsItemDto::getCategoryName, (o1, o2) -> o2));
        Map<String, List<CategoryGoodsDto.GoodsItemDto>> itemDict = new LinkedHashMap<>();
        for (CategoryGoodsDto.GoodsItemDto item : goodsItems) {
            itemDict.computeIfAbsent(item.getCategoryId(), k -> new ArrayList<>()).add(item);
        }
        List<CategoryGoodsDto> result = new ArrayList<>();
        itemDict.forEach((categoryId, goodsItem) -> result.add(new CategoryGoodsDto()
                .setCategoryId(categoryId)
                .setCategoryName(categoryNameDict.get(categoryId))
                .setGoodsItems(goodsItem)));
        return result;
    }

    @Override
    public List<CategoryGoodsDto.GoodsItemDto> listGoodsByCategoryId(String shopId, String categoryId) {
        List<CategoryGoodsDto.GoodsItemDto> goodsItems;
        if (LoginUtil.getUser() != null) {
            goodsItems = getBaseMapper().listGoodsItemsByUserIdAndCategoryId(shopId, categoryId, LoginUtil.getUser().getUserId());
        } else {
            goodsItems = getBaseMapper().listGoodsItemsByCategoryId(shopId, categoryId);
        }
        return goodsItems;
    }

}
