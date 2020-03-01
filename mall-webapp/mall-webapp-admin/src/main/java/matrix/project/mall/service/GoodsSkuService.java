package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsSku;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsSkuService extends IService<GoodsSku> {

    List<GoodsDto.GoodsSkuDto> queryByGoodsId(String goodsId);

}
