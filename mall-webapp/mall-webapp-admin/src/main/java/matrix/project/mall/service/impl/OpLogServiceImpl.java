package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.OpLog;
import matrix.project.mall.mapper.OpLogMapper;
import matrix.project.mall.service.OpLogService;
import matrix.project.mall.vo.QueryLogVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-21
 */
@Service
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements OpLogService {

    @Override
    public Integer countOpLog(QueryLogVo queryLogVo) {
        QueryWrapper<OpLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryLogVo.getName())) {
            queryWrapper.like("NAME", "%" + queryLogVo.getName() + "%");
        }
        return count(queryWrapper);
    }

    @Override
    public List<OpLog> listOpLog(QueryLogVo queryLogVo) {
        QueryWrapper<OpLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryLogVo.getName())) {
            queryWrapper.like("NAME", "%" + queryLogVo.getName() + "%");
        }
        queryWrapper.orderByDesc("CREATE_TIME");
        IPage<OpLog> page = new Page<>(queryLogVo.getPage(), queryLogVo.getPageSize());
        return page(page, queryWrapper).getRecords();
    }

}
