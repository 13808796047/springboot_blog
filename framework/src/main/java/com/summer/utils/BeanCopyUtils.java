package com.summer.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Summer
 * @since 2022/4/16 0:34
 */
public class BeanCopyUtils {
    public static <V> V copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V v = null;
        try {
            v = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return v;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
