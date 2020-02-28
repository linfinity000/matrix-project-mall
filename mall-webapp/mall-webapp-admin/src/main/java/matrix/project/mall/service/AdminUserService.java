package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.vo.LoginUserVo;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface AdminUserService extends IService<AdminUser> {

    void changePwd(String username, String originPassword, String newPassword);

    String login(LoginUserVo loginUserVo);

    void refreshAccessToken(String accessToken);

    AdminUser getUser(String accessToken);

    AdminUser queryByUsername(String username);

    AdminUser queryByUserId(String userId);
}
