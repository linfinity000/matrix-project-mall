package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.UserCartDto;
import matrix.project.mall.entity.UserCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Mapper
@TargetDataSource
public interface UserCartMapper extends BaseMapper<UserCart> {

    List<UserCartDto.UserCartItemDto> listCartItems(@Param("userId") String userId);

    Integer cartCount(@Param("userId") String userId);

    List<UserCartDto.UserCartItemDto> listCartItemsByCartIds(@Param("userId") String userId, @Param("cartIds") List<String> cartIds);

}
