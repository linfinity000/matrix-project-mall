package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.GoodsDto;
import matrix.project.mall.entity.GoodsAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface GoodsAttrMapper extends BaseMapper<GoodsAttr> {

    List<GoodsDto.GoodsAttrDto> queryByGoodsId(@Param("goodsId") String goodsId);
}
