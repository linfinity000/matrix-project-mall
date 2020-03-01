package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface GoodsSkuMapper extends BaseMapper<GoodsSku> {

    List<GoodsDto.GoodsSkuDto> queryByGoodsId(@Param("goodsId") String goodsId);

}
