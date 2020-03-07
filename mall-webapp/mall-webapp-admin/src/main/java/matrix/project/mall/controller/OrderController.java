package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.OrderService;
import matrix.project.mall.vo.OrderAddressVo;
import matrix.project.mall.vo.QueryOrderVo;
import matrix.project.mall.vo.ShipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderStatus")
    public Result orderStatus() {
        return Result.success(orderService.orderStatus());
    }

    @PostMapping("/countOrder")
    public Result countOrder(@RequestBody QueryOrderVo queryOrderVo) {
        return Result.success(orderService.countOrder(queryOrderVo));
    }

    @PostMapping("/listOrder")
    public Result listOrder(@RequestBody QueryOrderVo queryOrderVo) {
        return Result.success(orderService.listOrder(queryOrderVo));
    }

    @PostMapping("/saveOrderAddress")
    public Result saveOrderAddress(@RequestBody OrderAddressVo orderAddressVo) {
        return Result.success(orderService.saveOrderAddress(orderAddressVo));
    }

    @GetMapping("/listOrderGoods")
    public Result listOrderGoods(@RequestParam String orderId) {
        return Result.success(orderService.listOrderGoods(orderId));
    }

    @PostMapping("/saveShip")
    public Result saveShip(@RequestBody ShipVo shipVo) {
        return Result.success(orderService.saveShip(shipVo));
    }
}
