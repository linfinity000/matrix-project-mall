package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.OrderPayGroup;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
public interface OrderPayGroupService extends IService<OrderPayGroup> {

    String savePayGroup(List<String> orderIds, List<String> shopIds, BigDecimal price, String payMode, Integer payChannel);

    List<OrderPayGroup> queryByWaitPayGroupIds(List<String> payGroupIds);
}
