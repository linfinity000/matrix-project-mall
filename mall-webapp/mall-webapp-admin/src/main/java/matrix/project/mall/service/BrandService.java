package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Brand;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface BrandService extends IService<Brand> {

    Integer countByShopId(String shopId);
}
