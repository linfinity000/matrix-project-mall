package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/listGoods")
    public Result listGoods(@RequestParam String shopId) {
        return Result.success(goodsService.listGoods(shopId));
    }

    @GetMapping("/listGoodsByCategoryId")
    @NotNeedUserVerify
    public Result listGoodsByCategoryId(@RequestParam String shopId, @RequestParam String categoryId) {
        return Result.success(goodsService.listGoodsByCategoryId(shopId, categoryId));
    }

}
