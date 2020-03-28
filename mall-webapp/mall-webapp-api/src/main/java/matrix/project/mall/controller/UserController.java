package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.UserService;
import matrix.project.mall.vo.LoginUserVo;
import matrix.project.mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/exit")
    public Result exit(HttpServletRequest request) {
        String accessToken = request.getHeader("Access-Token");
        return Result.success(userService.exit(accessToken));
    }

    @GetMapping("/getUser")
    public Result getUser(HttpServletRequest request) {
        String accessToken = request.getHeader("Access-Token");
        return Result.success(userService.getUser(accessToken));
    }
}
