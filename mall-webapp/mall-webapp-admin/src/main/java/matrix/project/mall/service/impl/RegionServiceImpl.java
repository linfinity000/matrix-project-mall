package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Region;
import matrix.project.mall.mapper.RegionMapper;
import matrix.project.mall.service.RegionService;
import matrix.project.mall.vo.RegionVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public boolean saveRegion(RegionVo regionVo) {
        Assert.state(queryByCode(regionVo.getCode()) == null, "code已存在");
        Assert.state(regionVo.getParentCode() == 0L || queryByCode(regionVo.getParentCode()) != null, "父code不存在");
        Region region = new Region()
                .setCode(regionVo.getCode())
                .setName(regionVo.getName())
                .setParentCode(regionVo.getParentCode())
                .setStatus(Constant.ENABLED);
        save(region);
        return true;
    }

    @Override
    public boolean removeRegion(Long code) {
        List<Region> region = listRegion(code);
        Assert.state(CollectionUtils.isEmpty(region), "节点下存在数据");
        Region result = queryByCode(code);
        Assert.state(result != null, "查询region不存在");
        assert result != null;
        result.setStatus(Constant.DISABLED);
        updateById(result);
        return true;
    }

    @Override
    public Region queryByCode(Long code) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE", code)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

}
