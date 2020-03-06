package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Mapper
@TargetDataSource
public interface AddressMapper extends BaseMapper<Address> {

    Integer updateNoDefault(@Param("userId") String userId);

}
