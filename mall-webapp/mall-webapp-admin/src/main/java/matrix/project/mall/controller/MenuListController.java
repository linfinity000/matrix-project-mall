package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.MenuListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
