package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.QueryShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@RestController
@RequestMapping("/api/shop")
public class ShopController implements Serializable {

    @Autowired
    private ShopService shopService;

    @PostMapping("/countShop")
    public Result countShop(@RequestBody QueryShopVo queryShopVo) {
        return Result.success(shopService.countShop(queryShopVo));
    }

    @GetMapping("/listValidShop")
    public Result listValidShop() {
        return Result.success(shopService.listValidShop());
    }

    @PostMapping("/listShop")
    public Result listShop(@RequestBody QueryShopVo queryShopVo) {
        return Result.success(shopService.listShop(queryShopVo));
    }

    @GetMapping("/getShop")
    public Result getShop() {
        return Result.success(shopService.getShop());
    }


}
