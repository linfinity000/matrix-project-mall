package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.OpLogService;
import matrix.project.mall.vo.QueryLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 2020-03-21
 */
@RestController
@RequestMapping("/api/log")
public class LogController implements Serializable {

    @Autowired
    private OpLogService opLogService;

    @PostMapping("/countOpLog")
    public Result countOpLog(@RequestBody QueryLogVo queryLogVo) {
        return Result.success(opLogService.countOpLog(queryLogVo));
    }

    @PostMapping("/listOpLog")
    public Result listOpLog(@RequestBody QueryLogVo queryLogVo) {
        return Result.success(opLogService.listOpLog(queryLogVo));
    }
}
