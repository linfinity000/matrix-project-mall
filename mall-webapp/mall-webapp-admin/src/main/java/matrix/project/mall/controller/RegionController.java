package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.RegionService;
import matrix.project.mall.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/listRegion")
    public Result listRegion(@RequestParam Long parentCode) {
        return Result.success(regionService.listRegion(parentCode));
    }

    @PostMapping("/addRegion")
    public Result addRegion(@RequestBody RegionVo regionVo) {
        return Result.success(regionService.addRegion(regionVo));
    }


    @GetMapping("/removeRegion")
    public Result removeRegion(@RequestParam Long code) {
        return Result.success(regionService.removeRegion(code));
    }
}
