package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.UserCartService;
import matrix.project.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@RestController
@RequestMapping("/user-cart")
public class UserCartController {

    @Autowired
    private UserCartService userCartService;

    @PostMapping("/saveCart")
    public Result saveCart(@RequestBody CartVo cartVo) {
        return Result.success(userCartService.saveCart(cartVo));
    }

    @GetMapping("/removeCart")
    public Result removeCart(@RequestParam String id) {
        return Result.success(userCartService.removeCart(id));
    }
}
