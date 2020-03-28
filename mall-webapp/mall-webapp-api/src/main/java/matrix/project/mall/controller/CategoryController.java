package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-03-22
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listCategoryItem")
    @NotNeedUserVerify
    public Result listCategoryItem(@RequestParam String shopId) {
        return Result.success(categoryService.listCategoryItem(shopId));
    }
}
