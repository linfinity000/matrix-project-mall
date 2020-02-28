package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.mapper.AdminUserMapper;
import matrix.project.mall.service.AdminUserService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {
}
