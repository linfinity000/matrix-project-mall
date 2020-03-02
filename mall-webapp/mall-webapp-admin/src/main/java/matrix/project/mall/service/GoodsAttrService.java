package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.GoodsAttr;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsAttrService extends IService<GoodsAttr> {

    void removeAttr(String goodsId);
}
