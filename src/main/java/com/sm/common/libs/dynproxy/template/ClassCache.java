package com.sm.common.libs.dynproxy.template;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 类缓存
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:58:02
 */
public class ClassCache {

  /**
   * 加载类缓存
   */
  private final Map<ClassLoader, Map<Set<Class<?>>, WeakReference<Class<?>>>> loaderToClassCache = new WeakHashMap<>();

  /**
   * 代理类生成器 @see ClassGenerator
   */
  private final ClassGenerator proxyClassGenerator;

  public ClassCache(ClassGenerator proxyClassGenerator) {
    this.proxyClassGenerator = proxyClassGenerator;
  }

  private Map<Set<Class<?>>, WeakReference<Class<?>>> getClassCache(ClassLoader classLoader) {
    Map<Set<Class<?>>, WeakReference<Class<?>>> cache = loaderToClassCache.get(classLoader);
    if (cache == null) {
      cache = new HashMap<>();
      loaderToClassCache.put(classLoader, cache);
    }

    return cache;
  }

  private Set<Class<?>> toClassCacheKey(Class<?>[] proxyClasses) {
    return new HashSet<Class<?>>(Arrays.asList(proxyClasses));
  }

  /**
   * 获取代理类
   * 
   * @param classLoader 类加载器
   * @param proxyClasses 需要代理的类
   * @return 代理类
   */
  public synchronized Class<?> getProxyClass(ClassLoader classLoader, Class<?>[] proxyClasses) {
    Map<Set<Class<?>>, WeakReference<Class<?>>> classCache = getClassCache(classLoader);
    Set<Class<?>> key = toClassCacheKey(proxyClasses);
    Class<?> proxyClass;
    Reference<Class<?>> proxyClassReference = classCache.get(key);

    if (proxyClassReference == null) {
      proxyClass = proxyClassGenerator.generateProxyClass(classLoader, proxyClasses);
      classCache.put(key, new WeakReference<Class<?>>(proxyClass));
    } else {
      synchronized (proxyClassReference) {
        proxyClass = proxyClassReference.get();
        if (proxyClass == null) {
          proxyClass = proxyClassGenerator.generateProxyClass(classLoader, proxyClasses);
          classCache.put(key, new WeakReference<Class<?>>(proxyClass));
        }
      }
    }

    return proxyClass;
  }
}
