package com.tangerino.blog.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
public class ConvertUtils {
    @SneakyThrows
    public static <T> T convert(Object source, Class<T> targetType) {
        T instance = targetType.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, instance);
        return instance;
    }
}
