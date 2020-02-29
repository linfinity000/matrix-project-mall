package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.MenuListService;
import matrix.project.mall.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@RestController
@RequestMapping("/menu-list")
public class MenuListController {

    @Autowired
    private MenuListService menuListService;

    @GetMapping("/menuTree")
    public Result menuTree() {
        return Result.success(menuListService.queryTree());
    }

    @PostMapping("/saveTree")
    public Result saveTree(@RequestBody MenuVo menuVo) {
        return Result.success(menuListService.saveTree(menuVo));
    }

    @GetMapping("/removeTree")
    public Result removeTree(@RequestParam String menuId) {
        return Result.success(menuListService.removeTree(menuId));
    }

}
