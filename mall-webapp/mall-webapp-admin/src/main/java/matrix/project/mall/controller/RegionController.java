package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/removeRegion")
    public Result removeRegion(@RequestParam Long code) {
        return Result.success(regionService.removeRegion(code));
    }
}
