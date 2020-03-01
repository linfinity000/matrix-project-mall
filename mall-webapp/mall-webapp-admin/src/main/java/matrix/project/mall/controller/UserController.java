package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.UserService;
import matrix.project.mall.vo.QueryUserVo;
import matrix.project.mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/countUser")
    public Result countUser(@RequestBody QueryUserVo queryUserVo) {
        return Result.success(userService.countUser(queryUserVo));
    }

    @PostMapping("/listUser")
    public Result listUser(@RequestBody QueryUserVo queryUserVo) {
        return Result.success(userService.listUser(queryUserVo));
    }

    @PostMapping("/saveUser")
    public Result saveUser(@RequestBody UserVo userVo) {
        return Result.success(userService.saveUser(userVo));
    }

    @GetMapping("/removeUser")
    public Result removeUser(@RequestParam String userId) {
        return Result.success(userService.removeUser(userId));
    }

}
