package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Brand;
import matrix.project.mall.vo.BrandVo;
import matrix.project.mall.vo.QueryBrandVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface BrandService extends IService<Brand> {

    Integer countByShopId(String shopId);

    Integer countBrand(QueryBrandVo queryBrandVo);

    List<Brand> listBrand(QueryBrandVo queryBrandVo);

    boolean saveBrand(BrandVo brandVo);

    boolean removeBrand(String brandId);

    Brand queryByBrandId(String brandId);
}
