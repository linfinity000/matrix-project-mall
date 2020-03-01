package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.Goods;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsService extends IService<Goods> {

    Integer countByAtomsGoodsId(String atomsGoodsId);

    Integer countGoods(QueryGoodsVo queryGoodsVo);

    List<GoodsDto> listGoods(QueryGoodsVo queryGoodsVo);

    GoodsDto getGoods(String goodsId);

    boolean saveGoods(GoodsVo goodsVo);

    boolean removeGoods(String goodsId);

    Goods queryByGoodsId(String goodsId);
}
