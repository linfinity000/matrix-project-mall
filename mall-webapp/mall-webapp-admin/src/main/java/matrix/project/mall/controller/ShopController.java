package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.ShopService;
import matrix.project.mall.vo.QueryShopVo;
import matrix.project.mall.vo.ShopVo;
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

    @GetMapping("/bindShop")
    public Result bindShop(@RequestParam String shopId) {
        return Result.success(shopService.bindShop(shopId));
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

    @PostMapping("/saveShop")
    public Result saveShop(@RequestBody ShopVo shopVo) {
        return Result.success(shopService.saveShop(shopVo));
    }

    @GetMapping("/removeShop")
    public Result removeShop(@RequestParam String shopId) {
        return Result.success(shopService.removeShop(shopId));
    }

}
