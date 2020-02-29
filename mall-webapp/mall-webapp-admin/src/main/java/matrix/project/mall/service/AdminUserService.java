package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.AdminUserDto;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.vo.AdminUserVo;
import matrix.project.mall.vo.LoginUserVo;

import java.util.List;

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

    List<AdminUserDto> listUser();

    boolean saveUser(AdminUserVo adminUserVo);

    boolean removeUser(String userId);
}
