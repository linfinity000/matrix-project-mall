package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.dto.SkuDto;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;
import matrix.project.mall.vo.QuerySkuLabelVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsService extends IService<Goods> {

    Integer countByAtomsGoodsId(String atomsGoodsId);

    Integer countGoods(QueryGoodsVo queryGoodsVo);

    List<GoodsDto> listGoods(QueryGoodsVo queryGoodsVo);

    String queryGoodsIdBySkuLabel(List<QuerySkuLabelVo> list);

    GoodsDto getGoods(String goodsId);

    boolean saveGoods(GoodsVo goodsVo);

    boolean removeGoods(String goodsId);

    Goods queryByGoodsId(String goodsId);

    List<SkuDto> skuLabels(String atomsGoodsId);

    GoodsDto getGoods(List<QuerySkuLabelVo> querySkuLabelVos);

    GoodsDto getGoodsByAtomsGoodsId(String atomsGoodsId);
}
