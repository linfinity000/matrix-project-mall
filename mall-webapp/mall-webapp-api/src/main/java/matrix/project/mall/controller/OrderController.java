package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.vo.OrderVo;
import matrix.project.mall.vo.QueryOrderListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/orderList")
    public Result orderList(@RequestBody QueryOrderListVo queryOrderListVo) {
        return Result.success(orderService.orderList(queryOrderListVo));
    }

    @GetMapping("/cancelOrder")
    public Result cancelOrder(@RequestParam String orderId) {
        return Result.success(orderService.cancelOrder(orderId));
    }
}
