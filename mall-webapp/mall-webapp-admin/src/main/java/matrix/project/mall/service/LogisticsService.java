package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Logistics;
import matrix.project.mall.vo.LogisticsVo;
import matrix.project.mall.vo.QueryLogisticsVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
public interface LogisticsService extends IService<Logistics> {

    List<Logistics> listLogistics(QueryLogisticsVo queryLogisticsVo);

    Integer countLogistics(QueryLogisticsVo queryLogisticsVo);

    boolean saveLogistics(LogisticsVo logisticsVo);

    boolean removeLogistics(String logisticsId);

    Logistics queryByLogisticsId(String id);
}
