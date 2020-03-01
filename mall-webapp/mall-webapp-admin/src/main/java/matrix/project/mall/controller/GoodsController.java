package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/countGoods")
    public Result countGoods(@RequestBody QueryGoodsVo queryGoodsVo) {
        return Result.success(goodsService.countGoods(queryGoodsVo));
    }

    @PostMapping("/listGoods")
    public Result listGoods(@RequestBody QueryGoodsVo queryGoodsVo) {
        return Result.success(goodsService.listGoods(queryGoodsVo));
    }

    @GetMapping("/getGoods")
    public Result getAtomsGoods(@RequestParam String goodsId) {
        return Result.success(goodsService.getGoods(goodsId));
    }

    @PostMapping("/saveGoods")
    public Result saveGoods(@RequestBody GoodsVo goodsVo) {
        return Result.success(goodsService.saveGoods(goodsVo));
    }

    @GetMapping("/removeGoods")
    public Result removeGoods(@RequestParam String goodsId) {
        return Result.success(goodsService.removeGoods(goodsId));
    }
}
