package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.AtomsGoodsService;
import matrix.project.mall.vo.AtomsGoodsVo;
import matrix.project.mall.vo.QueryAtomsGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@RestController
@RequestMapping("/api/atoms-goods")
public class AtomsGoodsController {

    @Autowired
    private AtomsGoodsService atomsGoodsService;

    @PostMapping("/countAtomsGoods")
    public Result countAtomsGoods(@RequestBody QueryAtomsGoodsVo queryAtomsGoodsVo) {
        return Result.success(atomsGoodsService.countAtomsGoods(queryAtomsGoodsVo));
    }

    @PostMapping("/listAtomsGoods")
    public Result listAtomsGoods(@RequestBody QueryAtomsGoodsVo queryAtomsGoodsVo) {
        return Result.success(atomsGoodsService.listAtomsGoods(queryAtomsGoodsVo));
    }

    @GetMapping("/getAtomsGoods")
    public Result getAtomsGoods(@RequestParam String atomsGoodsId) {
        return Result.success(atomsGoodsService.getAtomsGoods(atomsGoodsId));
    }

    @PostMapping("/saveAtomsGoods")
    public Result saveAtomsGoods(@RequestBody AtomsGoodsVo atomsGoodsVo) {
        return Result.success(atomsGoodsService.saveAtomsGoods(atomsGoodsVo));
    }

    @GetMapping("/removeAtomsGoods")
    public Result removeAtomsGoods(@RequestParam String atomsGoodsId) {
        return Result.success(atomsGoodsService.removeAtomsGoods(atomsGoodsId));
    }


}
