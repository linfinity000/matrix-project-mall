package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.helper.encrypt.MD5;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.UserDto;
import matrix.project.mall.entity.User;
import matrix.project.mall.mapper.UserMapper;
import matrix.project.mall.service.UserService;
import matrix.project.mall.utils.ListUtil;
import matrix.project.mall.vo.QueryUserVo;
import matrix.project.mall.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Integer countUser(QueryUserVo queryUserVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryUserVo.getUsername())) {
            queryWrapper.eq("USERNAME", queryUserVo.getUsername());
        }
        if (queryUserVo.getStatus() != null) {
            queryWrapper.eq("STATUS", queryUserVo.getStatus());
        } else {
            queryWrapper.ne("STATUS", Constant.DELETED);
        }
        return count(queryWrapper);
    }

    @Override
    public List<UserDto> listUser(QueryUserVo queryUserVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryUserVo.getUsername())) {
            queryWrapper.eq("USERNAME", queryUserVo.getUsername());
        }
        if (queryUserVo.getStatus() != null) {
            queryWrapper.eq("STATUS", queryUserVo.getStatus());
        } else {
            queryWrapper.ne("STATUS", Constant.DELETED);
        }
        queryWrapper.orderByDesc("UPDATE_TIME");
        IPage<User> page = new Page<>(queryUserVo.getPage(), queryUserVo.getPageSize());
        List<User> list = page(page, queryWrapper).getRecords();
        return ListUtil.copyList(list, UserDto.class);
    }

    @Override
    public boolean saveUser(UserVo userVo) {
        Assert.state(!StringUtils.isEmpty(userVo.getNickname()), "昵称不允许为空");
        Assert.state(userVo.getStatus() != null, "状态不允许为空");
        User user = null;
        if (!StringUtils.isEmpty(userVo.getUserId())) {
            user = queryByUserId(userVo.getUserId());
            Assert.state(user != null, "用户未找到");
        } else {
            Assert.state(!StringUtils.isEmpty(userVo.getUsername()), "用户名不允许为空");
            Assert.state(!StringUtils.isEmpty(userVo.getMobile()), "手机号不允许为空");
            Assert.state(!StringUtils.isEmpty(userVo.getPassword()), "密码不允许为空");
            Assert.state(queryByUsername(userVo.getUsername()) == null, "用户已存在");
            user = new User()
                    .setUserId(RandomUtil.getUUID())
                    .setUsername(userVo.getUsername())
                    .setMobile(userVo.getMobile())
                    .setCreateTime(new Date());
        }
        assert user != null;
        if (!StringUtils.isEmpty(userVo.getPassword())) {
            user.setPassword(MD5.get32(userVo.getPassword()));
        }
        user.setNickname(userVo.getNickname())
                .setUpdateTime(new Date())
                .setStatus(userVo.getStatus());
        if (!StringUtils.isEmpty(userVo.getUserId())) {
            updateById(user);
        } else {
            save(user);
        }
        return true;
    }

    @Override
    public boolean removeUser(String userId) {
        User user = queryByUserId(userId);
        Assert.state(user != null, "未找到用户");
        assert user != null;
        user.setStatus(Constant.DELETED);
        updateById(user);
        return true;
    }

    @Override
    public User queryByUserId(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", userId)
                .ne("STATUS", Constant.DELETED);
        return getOne(queryWrapper, false);
    }

    @Override
    public User queryByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("STATUS", Constant.DELETED)
                .and(wrapper -> wrapper.eq("USERNAME", username)
                        .or().eq("MOBILE", username));
        return getOne(queryWrapper, false);
    }
}
