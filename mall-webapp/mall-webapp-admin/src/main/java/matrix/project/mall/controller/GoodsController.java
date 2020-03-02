package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.GoodsService;
import matrix.project.mall.vo.GoodsVo;
import matrix.project.mall.vo.QueryGoodsVo;
import matrix.project.mall.vo.QuerySkuLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/skuLabels")
    public Result skuLabels(@RequestParam String atomsGoodsId) {
        return Result.success(goodsService.skuLabels(atomsGoodsId));
    }

    @PostMapping("/getGoods")
    public Result getGoods(@RequestBody List<QuerySkuLabelVo> querySkuLabelVo) {
        return Result.success(goodsService.getGoods(querySkuLabelVo));
    }

    @GetMapping("/getGoodsByAtomsGoodsId")
    public Result getGoodsByAtomsGoodsId(@RequestParam String atomsGoodsId) {
        return Result.success(goodsService.getGoodsByAtomsGoodsId(atomsGoodsId));
    }

    @GetMapping("/getGoods")
    public Result getGoods(@RequestParam String goodsId) {
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
