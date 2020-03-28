package matrix.project.mall.utils;

import matrix.project.mall.dto.UserDto;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class LoginUtil {

    private static final ThreadLocal<UserDto> USER = new ThreadLocal<>();

    public static UserDto getUser() {
        return USER.get();
    }

    public static void setUser(UserDto user) {
        USER.set(user);
    }
}
