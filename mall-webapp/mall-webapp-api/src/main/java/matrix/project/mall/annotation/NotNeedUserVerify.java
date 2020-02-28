package matrix.project.mall.annotation;

import java.lang.annotation.*;

/**
 * @author wangcheng
 * @date 2020-02-28
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotNeedUserVerify {
}
