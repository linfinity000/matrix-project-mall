package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.AdminUserDto;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.vo.AdminUserVo;
import matrix.project.mall.vo.LoginUserVo;
import matrix.project.mall.vo.QueryAdminUserVo;

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

    Integer countUser(QueryAdminUserVo queryAdminUserVo);

    List<AdminUserDto> listUser(QueryAdminUserVo queryAdminUserVo);

    boolean saveUser(AdminUserVo adminUserVo);

    boolean removeUser(String userId);

    boolean exit(String accessToken);
}
