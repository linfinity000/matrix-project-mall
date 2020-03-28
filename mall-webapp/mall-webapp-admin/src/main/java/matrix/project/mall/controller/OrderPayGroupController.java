package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.module.oplog.annotation.OpLog;
import matrix.project.mall.service.OrderPayGroupService;
import matrix.project.mall.vo.QueryPayGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@RestController
@RequestMapping("/api/order-pay-group")
public class OrderPayGroupController {

    @Autowired
    private OrderPayGroupService orderPayGroupService;

    @PostMapping("/countPayGroup")
    public Result countPayGroup(@RequestBody QueryPayGroupVo payGroupVo) {
        return Result.success(orderPayGroupService.countPayGroup(payGroupVo));
    }

    @PostMapping("/listPayGroup")
    public Result listPayGroup(@RequestBody QueryPayGroupVo payGroupVo) {
        return Result.success(orderPayGroupService.listPayGroup(payGroupVo));
    }

    @GetMapping("/checkOrderStatus")
    @OpLog("调用第三方更新充值订单状态")
    public Result checkOrderStatus(@RequestParam String payGroupId) {
        return Result.success(orderPayGroupService.checkOrderStatus(payGroupId));
    }

}
