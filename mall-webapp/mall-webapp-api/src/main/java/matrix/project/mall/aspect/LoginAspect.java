package matrix.project.mall.aspect;

import matrix.module.common.bean.Result;
import matrix.module.common.exception.GlobalControllerException;
import matrix.module.common.helper.Assert;
import matrix.module.oplog.utils.MatrixUserUtil;
import matrix.project.mall.annotation.NotNeedClientVerify;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.entity.User;
import matrix.project.mall.service.ClientService;
import matrix.project.mall.service.UserService;
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
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Around("execution(* matrix.project.mall.controller..*(..))")
    public Object loginAround(ProceedingJoinPoint joinPoint) {
        NotNeedUserVerify notNeedUserVerify = null;
        try {
            NotNeedClientVerify notNeedClientVerify = joinPoint.getTarget().getClass().getAnnotation(NotNeedClientVerify.class);
            if (notNeedClientVerify == null) {
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                if (method.getAnnotation(NotNeedClientVerify.class) != null) {
                    notNeedClientVerify = method.getAnnotation(NotNeedClientVerify.class);
                }
            }
            if (notNeedClientVerify != null) {
                return joinPoint.proceed(joinPoint.getArgs());
            }
            HttpServletRequest request = RequestUtil.getRequest();
            try {
                String clientToken = request.getHeader("Authorization");
                Assert.state(!StringUtils.isEmpty(clientToken)
                                && clientToken.startsWith("Basic ")
                                && clientToken.split("Basic ").length > 1,
                        "Authorization 不合法");
                clientToken = clientToken.split("Basic ")[1];
                Assert.state(!StringUtils.isEmpty(clientToken), "Authorization 不合法");
                clientService.refreshClientToken(clientToken);
            } catch (Exception e) {
                logger.error(e);
                return Result.fail(e.getMessage()).setResultCode(-1001);
            }
            notNeedUserVerify = joinPoint.getTarget().getClass().getAnnotation(NotNeedUserVerify.class);
            if (notNeedUserVerify == null) {
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                if (method.getAnnotation(NotNeedUserVerify.class) != null) {
                    notNeedUserVerify = method.getAnnotation(NotNeedUserVerify.class);
                }
            }
            if (notNeedUserVerify == null) {
                try {
                    String accessToken = request.getHeader("Access-Token");
                    Assert.state(!StringUtils.isEmpty(accessToken), "Access-Token 不合法");
                    userService.refreshAccessToken(accessToken);
                    User user = userService.getUser(accessToken);
                    LoginUtil.setUser(user);
                    MatrixUserUtil.setUserId(user.getUserId());
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
