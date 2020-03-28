package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.*;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.vo.QueryGoodsVo;
import matrix.project.mall.vo.QuerySkuLabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface GoodsMapper extends BaseMapper<Goods> {

    Integer countGoods(@Param("shopId") String shopId, @Param("item") QueryGoodsVo queryGoodsVo);

    List<GoodsDto> listGoods(IPage<Goods> page, @Param("shopId") String shopId, @Param("item") QueryGoodsVo queryGoodsVo);

    GoodsDto getGoods(@Param("goodsId") String goodsId, @Param("shopId") String shopId);

    List<SkuDto.SkuLabelDto> skuLabels(@Param("atomsGoodsId") String atomsGoodsId);

    String queryGoodsIdBySkuLabel(@Param("list") List<QuerySkuLabelVo> list, @Param("length") int size);

    List<AttrDto> attrLabels(@Param("atomsGoodsId") String atomsGoodsId, @Param("goodsId") String goodsId);

    List<GoodsNameDto> goodsNameList(@Param("goodsIds") List<String> goodsIds);

    List<CategoryGoodsDto.GoodsItemDto> listGoodsItemsByUserId(@Param("shopId") String shopId, @Param("userId") String userId);

    List<CategoryGoodsDto.GoodsItemDto> listGoodsItems(@Param("shopId") String shopId);

    List<CategoryGoodsDto.GoodsItemDto> listGoodsItemsByUserIdAndCategoryId(@Param("shopId") String shopId, @Param("categoryId") String categoryId, @Param("userId") String userId);

    List<CategoryGoodsDto.GoodsItemDto> listGoodsItemsByCategoryId(@Param("shopId") String shopId, @Param("categoryId") String categoryId);
}
