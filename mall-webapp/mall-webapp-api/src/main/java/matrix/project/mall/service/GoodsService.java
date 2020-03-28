package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.CategoryGoodsDto;
import matrix.project.mall.dto.GoodsNameDto;
import matrix.project.mall.entity.Goods;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsService extends IService<Goods> {

    Goods queryByGoodsId(String goodsId);

    List<Goods> queryByGoodsIds(List<String> goodsIds);

    List<GoodsNameDto> goodsNameList(List<String> goodsIds);

    List<CategoryGoodsDto> listGoods(String shopId);

    List<CategoryGoodsDto.GoodsItemDto> listGoodsByCategoryId(String shopId, String categoryId);

}
