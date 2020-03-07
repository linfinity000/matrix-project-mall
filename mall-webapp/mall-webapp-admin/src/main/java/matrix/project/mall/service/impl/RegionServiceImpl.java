package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.TreeUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.RegionDto;
import matrix.project.mall.entity.Region;
import matrix.project.mall.mapper.RegionMapper;
import matrix.project.mall.service.RegionService;
import matrix.project.mall.utils.ListUtil;
import matrix.project.mall.vo.RegionVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<RegionDto> regionTree() {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .orderByAsc("CODE");
        List<Region> regions = list(queryWrapper);
        if (CollectionUtils.isEmpty(regions)) {
            return new ArrayList<>();
        }
        List<RegionDto> result = ListUtil.copyList(regions, RegionDto.class);
        TreeUtil.toTree(result, new TreeUtil.Comparator<RegionDto>() {
            @Override
            public boolean isParentWithChild(RegionDto pre, RegionDto after) {
                if (pre.getCode().equals(after.getParentCode())) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean isTop(RegionDto region) {
                Long parentCode = region.getParentCode();
                if (parentCode != null && parentCode.equals(0L)) {
                    return true;
                }
                return false;
            }
        });
        return result;
    }

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
        Assert.state(!StringUtils.isEmpty(regionVo.getCode()), "code不允许为空");
        Assert.state(regionVo.getCode().toString().length() == 2, "code必须为两位数字");
        Long code = Long.valueOf((regionVo.getParentCode().equals(0L) ? "" : regionVo.getParentCode().toString()) + regionVo.getCode().toString());
        Assert.state(queryByCode(code) == null, "code已存在");
        Assert.state(regionVo.getParentCode() == 0L || queryByCode(regionVo.getParentCode()) != null, "父code不存在");
        Region region = new Region()
                .setCode(code)
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
        removeById(code);
        return true;
    }

    @Override
    public Region queryByCode(Long code) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE", code)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

    @Override
    public List<Region> queryByCodes(List<Long> codes) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("CODE", codes)
                .eq("STATUS", Constant.ENABLED);
        return list(queryWrapper);
    }

}
