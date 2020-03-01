package matrix.project.mall.utils;

import matrix.project.mall.dto.AdminUserDto;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class LoginUtil {

    private static final ThreadLocal<AdminUserDto> ADMIN_USER = new ThreadLocal<>();

    public static AdminUserDto getAdminUser() {
        return ADMIN_USER.get();
    }

    public static void setAdminUser(AdminUserDto user) {
        ADMIN_USER.set(user);
    }
}
