package matrix.project.mall.utils;

import matrix.module.common.exception.ServiceException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wangcheng
 * @date 2019/5/31
 */
public class ClassUtil {

    /**
     * 获取类泛型的类型
     *
     * @param self
     * @return
     */
    public static Type[] getClassGenerics(Object self) {
        return ((ParameterizedType) self.getClass().getGenericSuperclass()).getActualTypeArguments();
    }

    /**
     * new一个实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 递归处理Class字段变量
     * @param clazz
     * @param list
     */
    public static void parseClassField(Class<?> clazz, List<Field> list) {
        if (list == null) {
            throw new ServiceException("list必须new ArrayList()");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            list.add(field);
        }
        if (clazz.getSuperclass() != null) {
            ClassUtil.parseClassField(clazz.getSuperclass(), list);
        }
    }

    /**
     * 递归查找注解类型
     * @param clazz
     * @param annotationType
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationType) {
        T t = clazz.getAnnotation(annotationType);
        if (t == null && clazz.getSuperclass() != null) {
            return getAnnotation(clazz.getSuperclass(), annotationType);
        }
        return t;
    }
}
