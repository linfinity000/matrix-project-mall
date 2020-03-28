package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.PayService;
import matrix.project.mall.vo.DoPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/doPay")
    public Result doPay(@RequestBody DoPayVo payVo) {
        return Result.success(payService.getPayUrl(payVo));
    }
}
