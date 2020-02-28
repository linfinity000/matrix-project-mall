package matrix.project.mall.utils;

import matrix.module.common.helper.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcheng
 * @date 2019/6/1
 */
public class ListUtil {

    /**
     * list数据copy
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T, S> List<T> copyList(List<S> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (S s : list) {
                T t = ClassUtil.newInstance(clazz);
                BeanUtils.copyProperties(s, t);
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 判断list里面是否有值
     *
     * @param list
     * @return
     */
    public static boolean isExistValue(List<Object> list) {
        boolean isExist = false;
        for (Object obj : list) {
            if (!StringUtils.isEmpty(obj)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 处理字符串逗号分隔返回数据Long类型
     *
     * @param ids
     * @return
     */
    public static List<Long> asLong(String ids) {
        Assert.isNotNull(ids, "ids");
        String[] list = ids.split(",");
        List<Long> result = new ArrayList<>();
        for (String item : list) {
            if (item == null || item.length() <= 0) {
                continue;
            }
            result.add(Long.valueOf(item));
        }
        return result;
    }
}
