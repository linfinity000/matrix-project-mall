package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Goods;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface GoodsService extends IService<Goods> {

    Integer countByAtomsGoodsId(String atomsGoodsId);

}
