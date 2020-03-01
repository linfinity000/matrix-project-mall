package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.AtomsGoodsDto;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.vo.AtomsGoodsVo;
import matrix.project.mall.vo.QueryAtomsGoodsVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface AtomsGoodsService extends IService<AtomsGoods> {

    Integer countByShopId(String shopId);

    Integer countByCategoryId(String categoryId);

    Integer countByBrandId(String brandId);

    Integer countAtomsGoods(QueryAtomsGoodsVo queryAtomsGoodsVo);

    List<AtomsGoodsDto> listAtomsGoods(QueryAtomsGoodsVo queryAtomsGoodsVo);

    boolean saveAtomsGoods(AtomsGoodsVo atomsGoodsVo);

    boolean removeAtomsGoods(String atomsGoodsId);
}
