package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Region;
import matrix.project.mall.mapper.RegionMapper;
import matrix.project.mall.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<Region> listRegion(Long parentCode) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_CODE", parentCode)
                .eq("STATUS", Constant.ENABLED)
                .orderByAsc("CODE");
        return list(queryWrapper);
    }

    @Override
    public List<Region> queryByCodes(List<Long> codes) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("PARENT_CODE", codes)
                .eq("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

}
