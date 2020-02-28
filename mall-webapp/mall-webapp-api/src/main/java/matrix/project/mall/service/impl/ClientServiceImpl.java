package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.helper.encrypt.MD5;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Client;
import matrix.project.mall.mapper.ClientMapper;
import matrix.project.mall.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Client queryByClientId(String clientId) {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CLIENT_ID", clientId);
        return getOne(queryWrapper, false);
    }

    @Override
    public String exchangeClientToken(String clientId, String clientSecret) {
        Client client = this.queryByClientId(clientId);
        Assert.state(client != null, "客户端未被注册");
        assert client != null;
        Assert.state(client.getClientSecret().equals(clientSecret), "客户端密钥错误");
        ValueOperations<String, String> valueOperations =  redisTemplate.opsForValue();
        String clientToken = MD5.get32(RandomUtil.getUUID());
        String key = "clients:" + clientToken;
        valueOperations.set(key, clientId, Constant.CLIENT_EXPIRE_TIME, TimeUnit.MINUTES);
        return clientToken;
    }

    @Override
    public void refreshClientToken(String clientToken) {
        ValueOperations<String, String> valueOperations =  redisTemplate.opsForValue();
        String key = "clients:" + clientToken;
        String clientId = valueOperations.get(key);
        Assert.state(!StringUtils.isEmpty(clientId), "此token已失效，请重新获取");
        valueOperations.set(key, clientId, Constant.CLIENT_EXPIRE_TIME, TimeUnit.MINUTES);
    }


}
