package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.GoodsSku;
import matrix.project.mall.vo.GoodsVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsSkuService extends IService<GoodsSku> {

    void saveGoodsSku(String goodsId, List<GoodsVo.SkuLabel> skuLabels);

    void removeSku(String goodsId);
}
