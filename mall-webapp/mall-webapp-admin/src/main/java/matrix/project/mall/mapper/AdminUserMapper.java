package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("admin")
public interface AdminUserMapper extends BaseMapper<AdminUser> {
}
