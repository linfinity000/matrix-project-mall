package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Logistics;
import matrix.project.mall.mapper.LogisticsMapper;
import matrix.project.mall.service.LogisticsService;
import matrix.project.mall.vo.LogisticsVo;
import matrix.project.mall.vo.QueryLogisticsVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Override
    public List<Logistics> listLogistics(QueryLogisticsVo queryLogisticsVo) {
        QueryWrapper<Logistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        if (!StringUtils.isEmpty(queryLogisticsVo.getLogisticsName())) {
            queryWrapper.eq("LOGISTICS_NAME", queryLogisticsVo.getLogisticsName());
        }
        queryWrapper.orderByAsc("UPDATE_TIME");
        IPage<Logistics> page = new Page<>(queryLogisticsVo.getPage(), queryLogisticsVo.getPageSize());
        return page(page, queryWrapper).getRecords();
    }

    @Override
    public Integer countLogistics(QueryLogisticsVo queryLogisticsVo) {
        QueryWrapper<Logistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        if (!StringUtils.isEmpty(queryLogisticsVo.getLogisticsName())) {
            queryWrapper.eq("LOGISTICS_NAME", queryLogisticsVo.getLogisticsName());
        }
        return count(queryWrapper);
    }

    @Override
    public boolean saveLogistics(LogisticsVo logisticsVo) {
        Assert.state(!StringUtils.isEmpty(logisticsVo.getLogisticsName()), "快递公司名称不能为空");
        Logistics logistics;
        if (!StringUtils.isEmpty(logisticsVo.getLogisticsId())) {
            logistics = queryByLogisticsId(logisticsVo.getLogisticsId());
            Assert.state(logistics != null, "快递公司未找到");
        } else {
            logistics = new Logistics()
                    .setLogisticsId(RandomUtil.getUUID())
                    .setCreateTime(new Date())
                    .setStatus(Constant.ENABLED);
        }
        assert logistics != null;
        logistics.setLogisticsName(logisticsVo.getLogisticsName())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(logisticsVo.getLogisticsId())) {
            updateById(logistics);
        } else {
            save(logistics);
        }
        return true;
    }

    @Override
    public boolean removeLogistics(String logisticsId) {
        Logistics logistics = queryByLogisticsId(logisticsId);
        Assert.state(logistics != null, "快递公司未找到");
        assert logistics != null;
        logistics.setStatus(Constant.DELETED)
                .setUpdateTime(new Date());
        updateById(logistics);
        return true;
    }

    @Override
    public Logistics queryByLogisticsId(String id) {
        QueryWrapper<Logistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
                .eq("LOGISTICS_ID", id);
        return getOne(queryWrapper);
    }
}
