package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.Region;
import matrix.project.mall.mapper.RegionMapper;
import matrix.project.mall.service.RegionService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
}
