package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.module.oplog.annotation.OpLog;
import matrix.project.mall.service.BrandService;
import matrix.project.mall.vo.BrandVo;
import matrix.project.mall.vo.QueryBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/countBrand")
    public Result countBrand(@RequestBody QueryBrandVo queryBrandVo) {
        return Result.success(brandService.countBrand(queryBrandVo));
    }

    @PostMapping("/listBrand")
    public Result listBrand(@RequestBody QueryBrandVo queryBrandVo) {
        return Result.success(brandService.listBrand(queryBrandVo));
    }

    @PostMapping("/saveBrand")
    @OpLog("保存品牌")
    public Result saveBrand(@RequestBody BrandVo brandVo) {
        return Result.success(brandService.saveBrand(brandVo));
    }

    @GetMapping("/removeBrand")
    @OpLog("移除")
    public Result removeBrand(@RequestParam String brandId) {
        return Result.success(brandService.removeBrand(brandId));
    }
}
