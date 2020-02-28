package matrix.project.mall.utils;

import matrix.project.mall.entity.User;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class LoginUtil {

    private static final ThreadLocal<User> USER = new ThreadLocal<>();

    public static void setUser(User user) {
        USER.set(user);
    }

    public static User getUser() {
        return USER.get();
    }
}
