package matrix.project.mall.aspect;

import matrix.module.common.exception.GlobalControllerException;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Configuration
@Aspect
public class LoginAspect {

    @Autowired
    private UserService userService;

    @Around("execution(* matrix.project.mall.controller..*(..))")
    public Object loginAround(ProceedingJoinPoint joinPoint) {
        try {
            NotNeedUserVerify notNeedUserVerify = joinPoint.getTarget().getClass().getAnnotation(NotNeedUserVerify.class);
            if (notNeedUserVerify == null) {
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                if (method.getAnnotation(NotNeedUserVerify.class) != null) {
                    notNeedUserVerify = method.getAnnotation(NotNeedUserVerify.class);
                }
            }
            if (notNeedUserVerify == null) {
                //todo
            }
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new GlobalControllerException(e);
        }
    }
}
