package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.UserDto;
import matrix.project.mall.entity.User;
import matrix.project.mall.vo.LoginUserVo;
import matrix.project.mall.vo.UserVo;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface UserService extends IService<User> {

    String registerUser(UserVo userVo);

    void changePwd(String username, String originPassword, String newPassword);

    String login(LoginUserVo loginUserVo);

    void refreshAccessToken(String accessToken);

    UserDto getUser(String accessToken);

    User queryByUsername(String username);

    User queryByUserId(String userId);

    boolean exit(String accessToken);
}
