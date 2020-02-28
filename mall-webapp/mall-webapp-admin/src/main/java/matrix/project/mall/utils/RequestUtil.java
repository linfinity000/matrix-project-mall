package matrix.project.mall.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
public class RequestUtil {

    private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return REQUEST.get();
    }

    public static void setRequest(HttpServletRequest request) {
        REQUEST.set(request);
    }
}
