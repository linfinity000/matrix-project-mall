package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedClientVerify;
import matrix.project.mall.service.ClientService;
import matrix.project.mall.vo.QueryClientTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@RestController
@RequestMapping("/client")
@NotNeedClientVerify
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/exchangeClientToken")
    public Result exchangeClientToken(@RequestBody QueryClientTokenVo clientTokenVo) {
        return Result.success(clientService.exchangeClientToken(clientTokenVo.getClientId(), clientTokenVo.getClientSecret()));
    }
}
