package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.OpLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcheng
 * @date 2020-03-21
 */
@Mapper
@TargetDataSource("admin")
public interface OpLogMapper extends BaseMapper<OpLog> {
}
