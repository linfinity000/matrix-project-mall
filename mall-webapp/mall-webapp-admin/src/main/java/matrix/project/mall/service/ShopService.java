package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Shop;
import matrix.project.mall.vo.QueryShopVo;
import matrix.project.mall.vo.ShopVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface ShopService extends IService<Shop> {

    Shop getShop();

    boolean bindShop(String shopId);

    List<Shop> listShop(QueryShopVo queryShopVo);

    Integer countShop(QueryShopVo queryShopVo);

    Shop queryByShopId(String shopId);

    Shop queryDefaultShop();

    List<Shop> listValidShop();

    boolean saveShop(ShopVo shopVo);

    boolean removeShop(String shopId);
}
