package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.UserCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Mapper
@TargetDataSource
public interface UserCartMapper extends BaseMapper<UserCart> {
}
