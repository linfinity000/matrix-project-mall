package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Brand;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface BrandService extends IService<Brand> {

    List<Brand> queryByShopId(String shopId);

    Brand queryByBrandId(String brandId);

}
