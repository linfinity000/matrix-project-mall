package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.OpLog;
import matrix.project.mall.vo.QueryLogVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-21
 */
public interface OpLogService extends IService<OpLog> {

    Integer countOpLog(QueryLogVo queryLogVo);

    List<OpLog> listOpLog(QueryLogVo queryLogVo);
}
