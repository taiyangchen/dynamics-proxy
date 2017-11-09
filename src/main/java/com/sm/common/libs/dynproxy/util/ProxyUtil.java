package com.sm.common.libs.dynproxy.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import com.sm.common.libs.dynproxy.DefaultProxyCreator;
import com.sm.common.libs.dynproxy.ProxyCreator;
import com.sm.common.libs.util.CastUtil;

/**
 * 动态代理相关的工具类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:06:45
 */
public abstract class ProxyUtil {

  public static final Object[] EMPTY_ARGUMENTS = ArrayUtils.EMPTY_OBJECT_ARRAY;
  public static final Class<?>[] EMPTY_ARGUMENT_TYPES = ArrayUtils.EMPTY_CLASS_ARRAY;
  private static final Map<Class<?>, Class<?>> WRAPPER_CLASS_MAP;
  private static final Map<Class<?>, Object> NULL_VALUE_MAP;

  static {
    final Map<Class<?>, Class<?>> wrappers = new HashMap<Class<?>, Class<?>>();
    wrappers.put(Integer.TYPE, Integer.class);
    wrappers.put(Character.TYPE, Character.class);
    wrappers.put(Boolean.TYPE, Boolean.class);
    wrappers.put(Short.TYPE, Short.class);
    wrappers.put(Long.TYPE, Long.class);
    wrappers.put(Float.TYPE, Float.class);
    wrappers.put(Double.TYPE, Double.class);
    wrappers.put(Byte.TYPE, Byte.class);
    WRAPPER_CLASS_MAP = Collections.unmodifiableMap(wrappers);

    final Map<Class<?>, Object> nullValues = new HashMap<Class<?>, Object>();
    nullValues.put(Integer.TYPE, Integer.valueOf(0));
    nullValues.put(Long.TYPE, Long.valueOf(0));
    nullValues.put(Short.TYPE, Short.valueOf((short) 0));
    nullValues.put(Byte.TYPE, Byte.valueOf((byte) 0));
    nullValues.put(Float.TYPE, Float.valueOf(0.0f));
    nullValues.put(Double.TYPE, Double.valueOf(0.0));
    nullValues.put(Character.TYPE, Character.valueOf((char) 0));
    nullValues.put(Boolean.TYPE, Boolean.FALSE);
    NULL_VALUE_MAP = Collections.unmodifiableMap(nullValues);
  }

  public static ProxyCreator getInstance() {
    return DefaultProxyCreator.INSTANCE;
  }

  private ProxyUtil() {

  }

  /**
   * 获取类所有接口
   * 
   * @param cls 需要处理的类
   * @return 类所有接口
   */
  public static Class<?>[] getAllInterfaces(Class<?> cls) {
    return cls == null ? null : ClassUtils.getAllInterfaces(cls).toArray(ArrayUtils.EMPTY_CLASS_ARRAY);
  }

  /**
   * 获取类的java名称
   * 
   * @param clazz 需要处理的类
   * @return 类的java名称
   */
  public static String getJavaClassName(Class<?> clazz) {
    if (clazz.isArray()) {
      return getJavaClassName(clazz.getComponentType()) + "[]";
    }
    return clazz.getName();
  }

  /**
   * 获取包装类型
   * 
   * @param primitiveType 基本类型
   * @return 包装类型
   */
  public static Class<?> getWrapperClass(Class<?> primitiveType) {
    return WRAPPER_CLASS_MAP.get(primitiveType);
  }

  /**
   * 获取类型的缺省值
   * 
   * @param type 类型
   * @return 缺省值
   */
  public static <T> T nullValue(Class<T> type) {
    Object value = NULL_VALUE_MAP.get(type);

    return CastUtil.cast(value);
  }

  /**
   * 是否为 {@link #equals(Object)} 方法
   * 
   * @param method 方法
   * @return 是否为 {@link #equals(Object)} 方法
   */
  public static boolean isEqualsMethod(Method method) {
    return "equals".equals(method.getName()) && method.getParameterTypes().length == 1
        && Object.class.equals(method.getParameterTypes()[0]);
  }

  /**
   * 是否为 {@link #hashCode(Object)} 方法
   * 
   * @param method 方法
   * @return 是否为 {@link #hashCode(Object)} 方法
   */
  public static boolean isHashCode(Method method) {
    return "hashCode".equals(method.getName()) && method.getParameterTypes().length == 0;
  }

}
