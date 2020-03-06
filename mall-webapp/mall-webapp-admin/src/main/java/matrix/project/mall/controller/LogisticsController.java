package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.LogisticsService;
import matrix.project.mall.vo.LogisticsVo;
import matrix.project.mall.vo.QueryLogisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/listLogistics")
    public Result listLogistics(@RequestBody QueryLogisticsVo queryLogisticsVo) {
        return Result.success(logisticsService.listLogistics(queryLogisticsVo));
    }

    @PostMapping("/countLogistics")
    public Result countLogistics(@RequestBody QueryLogisticsVo queryLogisticsVo) {
        return Result.success(logisticsService.countLogistics(queryLogisticsVo));
    }

    @PostMapping("/saveLogistics")
    public Result saveLogistics(@RequestBody LogisticsVo logisticsVo) {
        return Result.success(logisticsService.saveLogistics(logisticsVo));
    }

    @GetMapping("/removeLogistics")
    public Result removeLogistics(@RequestParam String logisticsId) {
        return Result.success(logisticsService.removeLogistics(logisticsId));
    }
}
