package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.OrderGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@Mapper
@TargetDataSource
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {
}
