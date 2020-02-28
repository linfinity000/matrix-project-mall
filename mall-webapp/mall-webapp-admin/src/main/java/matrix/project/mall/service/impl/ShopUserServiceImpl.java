package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.ShopUser;
import matrix.project.mall.mapper.ShopUserMapper;
import matrix.project.mall.service.ShopUserService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class ShopUserServiceImpl extends ServiceImpl<ShopUserMapper, ShopUser> implements ShopUserService {
}
