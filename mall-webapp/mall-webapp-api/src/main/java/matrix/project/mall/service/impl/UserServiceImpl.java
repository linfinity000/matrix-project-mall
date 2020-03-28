package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.helper.encrypt.MD5;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.UserDto;
import matrix.project.mall.entity.User;
import matrix.project.mall.mapper.UserMapper;
import matrix.project.mall.service.UserService;
import matrix.project.mall.vo.LoginUserVo;
import matrix.project.mall.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String registerUser(UserVo userVo) {
        Assert.state(!StringUtils.isEmpty(userVo.getMobile()), "手机号不允许为空");
        Assert.state(!StringUtils.isEmpty(userVo.getPassword()), "密码不允许为空");
        Assert.state(queryByUsername(userVo.getMobile()) == null, "手机号已存在");
        if (StringUtils.isEmpty(userVo.getUsername())) {
            userVo.setUsername(userVo.getMobile());
        } else {
            Assert.state(queryByUsername(userVo.getUsername()) == null, "用户名已存在");
        }
        if (StringUtils.isEmpty(userVo.getNickname())) {
            userVo.setNickname(RandomUtil.getUUID());
        }
        User user = new User()
                .setUserId(RandomUtil.getUUID())
                .setNickname(userVo.getNickname())
                .setMobile(userVo.getMobile())
                .setUsername(userVo.getUsername())
                .setPassword(MD5.get32(userVo.getPassword()))
                .setCreateTime(new Date())
                .setUpdateTime(new Date())
                .setStatus(Constant.ENABLED);
        save(user);
        return login(new LoginUserVo().setUsername(userVo.getMobile()).setPassword(userVo.getPassword()));
    }

    @Override
    public void changePwd(String username, String originPassword, String newPassword) {
        User user = queryByUsername(username);
        Assert.state(user != null, "用户不存在");
        assert user != null;
        Assert.state(user.getStatus().equals(Constant.ENABLED), "用户被禁用");
        Assert.state(user.getPassword().equals(MD5.get32(originPassword)), "密码错误");
        user.setPassword(MD5.get32(newPassword))
                .setUpdateTime(new Date());
        updateById(user);
    }

    @Override
    public String login(LoginUserVo loginUserVo) {
        Assert.state(!StringUtils.isEmpty(loginUserVo.getUsername()), "用户名不允许为空");
        Assert.state(!StringUtils.isEmpty(loginUserVo.getPassword()), "密码不允许为空");
        User user = queryByUsername(loginUserVo.getUsername());
        Assert.state(user != null, "用户不存在");
        assert user != null;
        Assert.state(user.getStatus().equals(Constant.ENABLED), "用户被禁用");
        Assert.state(user.getPassword().equals(MD5.get32(loginUserVo.getPassword())), "密码错误");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String accessToken = RandomUtil.getUUID();
        String key = "login:" + accessToken;
        valueOperations.set(key, user.getUserId(), Constant.LOGIN_EXPIRE_TIME, TimeUnit.MINUTES);
        return accessToken;
    }

    @Override
    public void refreshAccessToken(String accessToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "login:" + accessToken;
        String value = valueOperations.get(key);
        Assert.state(!StringUtils.isEmpty(value), "Access-Token 已过期请重新获取");
        valueOperations.set(key, value, Constant.LOGIN_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    @Override
    public UserDto getUser(String accessToken) {
        String key = "login:" + accessToken;
        String userId = redisTemplate.opsForValue().get(key);
        User user = queryByUserId(userId);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Override
    public User queryByUserId(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED)
            .eq("USER_ID", userId);
        return getOne(queryWrapper, false);
    }

    @Override
    public boolean exit(String accessToken) {
        String key = "login:" + accessToken;
        redisTemplate.delete(key);
        return true;
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
