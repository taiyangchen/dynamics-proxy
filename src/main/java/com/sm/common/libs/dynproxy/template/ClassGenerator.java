package com.sm.common.libs.dynproxy.template;

/**
 * 代理类生成器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:58:10
 */
public interface ClassGenerator {

  /**
   * 生成代理类
   * 
   * @param classLoader 类加载器
   * @param proxyClasses 需要代理的类
   * @return 代理类
   */
  Class<?> generateProxyClass(ClassLoader classLoader, Class<?>... proxyClasses);

}
