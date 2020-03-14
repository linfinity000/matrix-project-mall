package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.module.oplog.annotation.OpLog;
import matrix.project.mall.service.MenuService;
import matrix.project.mall.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menuTree")
    public Result menuTree() {
        return Result.success(menuService.queryTree());
    }

    @PostMapping("/saveTree")
    @OpLog("新增菜单")
    public Result saveTree(@RequestBody MenuVo menuVo) {
        return Result.success(menuService.saveTree(menuVo));
    }

    @GetMapping("/removeTree")
    @OpLog("移除菜单")
    public Result removeTree(@RequestParam String menuId) {
        return Result.success(menuService.removeTree(menuId));
    }

}
