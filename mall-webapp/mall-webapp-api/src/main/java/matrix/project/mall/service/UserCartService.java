package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.UserCart;
import matrix.project.mall.vo.CartVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
public interface UserCartService extends IService<UserCart> {

    boolean saveCart(CartVo cartVo);

    UserCart queryByGoodsId(String goodsId);

    UserCart queryById(String id);

    List<UserCart> queryByIds(List<String> ids);

    boolean removeCart(String id);
}
