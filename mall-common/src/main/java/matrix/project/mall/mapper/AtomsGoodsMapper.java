package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.dto.AtomsGoodsDto;
import matrix.project.mall.entity.AtomsGoods;
import matrix.project.mall.vo.QueryAtomsGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface AtomsGoodsMapper extends BaseMapper<AtomsGoods> {

    List<AtomsGoodsDto> listAtomsGoods(IPage<AtomsGoods> page, @Param("item") QueryAtomsGoodsVo queryAtomsGoodsVo, @Param("shopId") String shopId);

    AtomsGoodsDto getAtomsGoods(@Param("atomsGoodsId") String atomsGoodsId, @Param("shopId") String shopId);
}
