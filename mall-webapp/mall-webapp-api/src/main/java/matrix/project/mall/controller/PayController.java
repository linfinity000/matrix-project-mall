package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/toPay")
    public Result toPay(@RequestParam String orderId) {
        return Result.success(payService.getPayUrl(orderId));
    }
}
