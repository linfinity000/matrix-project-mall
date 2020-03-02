package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.UserCartService;
import matrix.project.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@RestController
@RequestMapping("/user-cart")
public class UserCartController {

    @Autowired
    private UserCartService userCartService;

    @PostMapping("/updateCart")
    public Result updateCart(@RequestBody CartVo cartVo) {
        return Result.success(userCartService.updateCart(cartVo));
    }

}
