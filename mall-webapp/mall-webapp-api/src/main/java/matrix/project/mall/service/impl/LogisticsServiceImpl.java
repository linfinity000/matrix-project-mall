package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.Logistics;
import matrix.project.mall.mapper.LogisticsMapper;
import matrix.project.mall.service.LogisticsService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {
}
