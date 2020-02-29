package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.UserDto;
import matrix.project.mall.entity.User;
import matrix.project.mall.vo.QueryUserVo;
import matrix.project.mall.vo.UserVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public interface UserService extends IService<User> {

    Integer countUser(QueryUserVo queryUserVo);

    List<UserDto> listUser(QueryUserVo queryUserVo);

    boolean saveUser(UserVo userVo);

    boolean removeUser(String userId);

    User queryByUserId(String userId);

    User queryByUsername(String username);
}
