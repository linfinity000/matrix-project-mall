package matrix.project.mall.utils;

import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.entity.ShopUser;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class LoginUtil {

    private static final ThreadLocal<AdminUser> ADMIN_USER = new ThreadLocal<>();

    private static final ThreadLocal<ShopUser> SHOP_USER = new ThreadLocal<>();

    public static AdminUser getAdminUser() {
        return ADMIN_USER.get();
    }

    public static void setAdminUser(AdminUser user) {
        ADMIN_USER.set(user);
    }

    public static ShopUser getShopUser() {
        return SHOP_USER.get();
    }

    public static void setShopUser(ShopUser user) {
        SHOP_USER.set(user);
    }
}
