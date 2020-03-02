package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.UserService;
import matrix.project.mall.vo.LoginUserVo;
import matrix.project.mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @NotNeedUserVerify
    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody UserVo userVo) {
        return Result.success(userService.registerUser(userVo));
    }

    @NotNeedUserVerify
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserVo loginUserVo) {
        return Result.success(userService.login(loginUserVo));
    }
}
