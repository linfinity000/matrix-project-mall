package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Brand;
import matrix.project.mall.mapper.BrandMapper;
import matrix.project.mall.service.BrandService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Override
    public Integer countByShopId(String shopId) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SHOP_ID", shopId)
                .ne("STATUS", Constant.DELETED);
        return count(queryWrapper);
    }
}
