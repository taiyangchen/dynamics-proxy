package com.sm.common.libs.dynproxy.template;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sm.common.libs.dynproxy.MethodSignature;

/**
 * 代理类生成器模版
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:58:35
 */
public abstract class GeneratorTemplate implements ClassGenerator {

  /**
   * 获取代理类所实现的所有方法
   * 
   * @param proxyClasses 代理类
   * @return 代理类所实现的所有方法
   */
  public static Method[] getImplementationMethods(Class<?>[] proxyClasses) {
    Map<MethodSignature, Method> signatureMethodMap = new HashMap<MethodSignature, Method>();
    final Set<MethodSignature> finalizedSignatures = new HashSet<MethodSignature>();

    for (int i = 0; i < proxyClasses.length; i++) {
      Class<?> proxyInterface = proxyClasses[i];
      Method[] methods = proxyInterface.getMethods();
      for (int j = 0; j < methods.length; j++) {
        MethodSignature signature = new MethodSignature(methods[j]);
        if (Modifier.isFinal(methods[j].getModifiers())) {
          finalizedSignatures.add(signature);
        } else if (!signatureMethodMap.containsKey(signature)) {
          signatureMethodMap.put(signature, methods[j]);
        }
      }
    }

    Collection<Method> resultingMethods = signatureMethodMap.values();
    for (MethodSignature signature : finalizedSignatures) {
      resultingMethods.remove(signatureMethodMap.get(signature));
    }

    return resultingMethods.toArray(new Method[resultingMethods.size()]);
  }
}
