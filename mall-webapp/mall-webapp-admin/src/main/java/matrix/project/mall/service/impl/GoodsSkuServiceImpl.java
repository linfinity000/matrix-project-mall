package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsSku;
import matrix.project.mall.mapper.GoodsSkuMapper;
import matrix.project.mall.service.GoodsSkuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {
    @Override
    public List<GoodsDto.GoodsSkuDto> queryByGoodsId(String goodsId) {
        return getBaseMapper().queryByGoodsId(goodsId);
    }
}
