package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.UserCart;
import matrix.project.mall.mapper.UserCartMapper;
import matrix.project.mall.service.UserCartService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-02
 */
@Service
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart> implements UserCartService {
}
