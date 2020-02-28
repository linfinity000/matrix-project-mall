package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Client;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface ClientService extends IService<Client> {

    Client queryByClientId(String clientId);

    String exchangeClientToken(String clientId, String clientSecret);

    void refreshClientToken(String clientToken);

}
