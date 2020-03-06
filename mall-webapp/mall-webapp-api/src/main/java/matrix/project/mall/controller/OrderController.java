package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public Result saveOrder(@RequestBody OrderVo orderVo) {
        return Result.success(orderService.saveOrder(orderVo));
    }
}
