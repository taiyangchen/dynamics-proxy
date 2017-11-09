package com.sm.common.libs.dynproxy.template;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

import com.sm.common.libs.dynproxy.exception.ProxyCreatorException;

/**
 * 子类代理器创建模版
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2015-3-9 下午10:50:38
 */
public abstract class SubclassCreatorTemplate extends CreatorTemplate {

  @Override
  public boolean canProxy(Class<?>... proxyClasses) {
    try {
      getSuperclass(proxyClasses);
      return true;
    } catch (ProxyCreatorException e) {
      return false;
    }
  }

  // FIXME move?
  private static boolean hasDefaultConstructor(Class<?> superclass) {
    final Constructor<?>[] declaredConstructors = superclass.getDeclaredConstructors();
    for (int i = 0; i < declaredConstructors.length; i++) {
      Constructor<?> constructor = declaredConstructors[i];
      if (constructor.getParameterTypes().length == 0
          && (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers()))) {
        return true;
      }
    }

    return false;
  }

  private static Class<?>[] toNonInterfaces(Class<?>[] proxyClasses) {
    Set<Class<?>> superclasses = new LinkedHashSet<Class<?>>();
    for (Class<?> proxyClass : proxyClasses) {
      if (!proxyClass.isInterface()) {
        superclasses.add(proxyClass);
      }
    }

    return superclasses.toArray(new Class[superclasses.size()]);
  }

  // FIXME move?
  protected static Class<?>[] toInterfaces(Class<?>[] proxyClasses) {
    Set<Class<?>> interfaces = new LinkedHashSet<Class<?>>();
    for (Class<?> proxyClass : proxyClasses) {
      if (proxyClass.isInterface()) {
        interfaces.add(proxyClass);
      }
    }

    interfaces.add(Serializable.class);
    return interfaces.toArray(new Class[interfaces.size()]);
  }

  // FIXME move?
  public static Class<?> getSuperclass(Class<?>[] proxyClasses) {
    final Class<?>[] superclasses = toNonInterfaces(proxyClasses);
    switch (superclasses.length) {
      case 0:
        return Object.class;
      case 1:
        Class<?> superclass = superclasses[0];
        if (Modifier.isFinal(superclass.getModifiers())) {
          throw new ProxyCreatorException("Proxy class cannot extend {} as it is final.", superclass.getName());
        }
        if (!hasDefaultConstructor(superclass)) {
          throw new ProxyCreatorException(
              "Proxy class cannot extend {}, because it has no visible \"default\" constructor.", superclass.getName());
        }

        return superclass;
      default:
        StringBuilder errorMessage = new StringBuilder("Proxy class cannot extend ");
        for (int i = 0; i < superclasses.length; i++) {
          Class<?> c = superclasses[i];
          errorMessage.append(c.getName());
          if (i != superclasses.length - 1) {
            errorMessage.append(", ");
          }
        }

        errorMessage.append("; multiple inheritance not allowed.");
        throw new ProxyCreatorException(errorMessage.toString());
    }
  }
}
