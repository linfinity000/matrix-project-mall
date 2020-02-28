package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.AdminUserService;
import matrix.project.mall.vo.LoginUserVo;
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
@RequestMapping("/admin-user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @NotNeedUserVerify
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserVo loginUserVo) {
        return Result.success(adminUserService.login(loginUserVo));
    }

}
