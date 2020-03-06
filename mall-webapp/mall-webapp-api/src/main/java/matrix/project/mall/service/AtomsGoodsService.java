package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.AtomsGoods;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface AtomsGoodsService extends IService<AtomsGoods> {

    AtomsGoods queryById(String atomsGoodsId);

    List<AtomsGoods> queryByIds(List<String> atomsGoodsIds);

}
