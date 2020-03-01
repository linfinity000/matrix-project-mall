package matrix.project.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import matrix.module.jdbc.annotation.TargetDataSource;
import matrix.project.mall.entity.AtomsGoodsBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Mapper
@TargetDataSource("master")
public interface AtomsGoodsBannerMapper extends BaseMapper<AtomsGoodsBanner> {

    void deleteByAtomsGoodsId(@Param("atomsGoodsId") String atomsGoodsId);

}
