package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.AtomsGoods;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface AtomsGoodsService extends IService<AtomsGoods> {

    Integer countByShopId(String shopId);

    Integer countByCategoryId(String categoryId);

    Integer countByBrandId(String brandId);
}
