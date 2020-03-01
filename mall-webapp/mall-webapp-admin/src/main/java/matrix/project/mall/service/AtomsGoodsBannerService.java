package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.AtomsGoodsBanner;
import matrix.project.mall.vo.AtomsGoodsVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface AtomsGoodsBannerService extends IService<AtomsGoodsBanner> {

    List<AtomsGoodsBanner> queryByAtomsGoodsId(String atomsGoodsId);

    void saveAtomsGoodsBanner(String atomsGoodsId, List<AtomsGoodsVo.Banner> banners);

}
