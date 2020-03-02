package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.UserCart;
import matrix.project.mall.vo.CartVo;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
public interface UserCartService extends IService<UserCart> {

    boolean updateCart(CartVo cartVo);

    UserCart queryByGoodsId(String goodsId);

}
