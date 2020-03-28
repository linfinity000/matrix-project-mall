package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.OrderPayGroup;
import matrix.project.mall.vo.QueryPayGroupVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
public interface OrderPayGroupService extends IService<OrderPayGroup> {

    Integer countPayGroup(QueryPayGroupVo payGroupVo);

    List<OrderPayGroup> listPayGroup(QueryPayGroupVo payGroupVo);

    OrderPayGroup queryById(String id);

    boolean checkOrderStatus(String payGroupId);
}
