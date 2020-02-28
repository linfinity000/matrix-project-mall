package matrix.project.mall.utils;

import matrix.project.mall.entity.AdminUser;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class LoginUtil {

    private static final ThreadLocal<AdminUser> ADMIN_USER = new ThreadLocal<>();

    public static AdminUser getAdminUser() {
        return ADMIN_USER.get();
    }

    public static void setAdminUser(AdminUser user) {
        ADMIN_USER.set(user);
    }
}
