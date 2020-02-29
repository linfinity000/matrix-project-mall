package matrix.project.mall.aspect;

import matrix.module.common.bean.Result;
import matrix.module.common.exception.GlobalControllerException;
import matrix.module.common.helper.Assert;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.AdminUserService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.utils.RequestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Configuration
@Aspect
public class LoginAspect {

    private static final Logger logger = LogManager.getLogger(LoginAspect.class);

    @Autowired
    private AdminUserService adminUserService;

    @Around("execution(* matrix.project.mall.controller..*(..))")
    public Object loginAround(ProceedingJoinPoint joinPoint) {
        NotNeedUserVerify notNeedUserVerify = joinPoint.getTarget().getClass().getAnnotation(NotNeedUserVerify.class);
        try {
            if (notNeedUserVerify == null) {
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                if (method.getAnnotation(NotNeedUserVerify.class) != null) {
                    notNeedUserVerify = method.getAnnotation(NotNeedUserVerify.class);
                }
            }
            if (notNeedUserVerify == null) {
                try {
                    HttpServletRequest request = RequestUtil.getRequest();
                    String accessToken = request.getHeader("Access-Token");
                    Assert.state(!StringUtils.isEmpty(accessToken), "Access-Token 不合法");
                    adminUserService.refreshAccessToken(accessToken);
                    LoginUtil.setAdminUser(adminUserService.getUser(accessToken));
                } catch (Exception e) {
                    logger.error(e);
                    return Result.fail(e.getMessage()).setResultCode(-1000);
                }
            }
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new GlobalControllerException(e);
        }
    }
}
