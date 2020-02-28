package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-02-27
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Result test() {
        return Result.success("asd");
    }
}
