package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsAttr;
import matrix.project.mall.mapper.GoodsAttrMapper;
import matrix.project.mall.service.GoodsAttrService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class GoodsAttrServiceImpl extends ServiceImpl<GoodsAttrMapper, GoodsAttr> implements GoodsAttrService {

    @Override
    public List<GoodsDto.GoodsAttrDto> queryByGoodsId(String goodsId) {
        return getBaseMapper().queryByGoodsId(goodsId);
    }

}
