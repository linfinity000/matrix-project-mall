package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Region;
import matrix.project.mall.vo.RegionVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
public interface RegionService extends IService<Region> {

    List<Region> listRegion(Long parentCode);

    boolean addRegion(RegionVo regionVo);

    boolean removeRegion(Long code);

    Region queryByCode(Long code);
}
