package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.MenuService;
import matrix.project.mall.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menuTree")
    public Result menuTree() {
        return Result.success(menuService.queryTree());
    }

    @PostMapping("/saveTree")
    public Result saveTree(@RequestBody MenuVo menuVo) {
        return Result.success(menuService.saveTree(menuVo));
    }

    @GetMapping("/removeTree")
    public Result removeTree(@RequestParam String menuId) {
        return Result.success(menuService.removeTree(menuId));
    }

}
