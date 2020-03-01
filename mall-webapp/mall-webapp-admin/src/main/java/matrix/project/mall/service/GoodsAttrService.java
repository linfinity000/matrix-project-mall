package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsAttr;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsAttrService extends IService<GoodsAttr> {

    List<GoodsDto.GoodsAttrDto> queryByGoodsId(String goodsId);

}
