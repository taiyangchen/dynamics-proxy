package com.sm.common.libs.dynproxy;

/**
 * 动态代理创建接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:53:35
 */
public interface ProxyCreator {

  /**
   * 判断提供的类能否生成动态代理
   * 
   * @param proxyClasses 提供的类
   * @return 能否生成动态代理
   */
  boolean canProxy(Class<?>... proxyClasses);

  /**
   * 创建委托代理对象
   * 
   * @param delegateProvider 委托的对象提供者 @see ObjectProvider
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createDelegatorProxy(ObjectProvider<?> delegateProvider, Class<?>... proxyClasses);

  /**
   * 创建委托代理对象
   * 
   * @param classLoader 类加载器
   * @param delegateProvider 委托的对象提供者 @see ObjectProvider
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createDelegatorProxy(ClassLoader classLoader, ObjectProvider<?> delegateProvider, Class<?>... proxyClasses);

  /**
   * 创建拦截代理对象
   * 
   * @param target 目标对象
   * @param interceptor 拦截器 @see Interceptor
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createInterceptorProxy(Object target, Interceptor interceptor, Class<?>... proxyClasses);

  /**
   * 创建拦截代理对象
   * 
   * @param classLoader 类加载器
   * @param target 目标对象
   * @param interceptor 拦截器 @see Interceptor
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createInterceptorProxy(ClassLoader classLoader, Object target, Interceptor interceptor,
      Class<?>... proxyClasses);

  /**
   * 创建调用代理对象
   * 
   * @param invoker 对象调用接口 @see ObjectInvoker
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createInvokerProxy(ObjectInvoker invoker, Class<?>... proxyClasses);

  /**
   * 创建调用代理对象
   * 
   * @param classLoader 类加载器
   * @param invoker 对象调用接口 @see ObjectInvoker
   * @param proxyClasses 需要代理的类
   * @return 代理对象
   */
  <T> T createInvokerProxy(ClassLoader classLoader, ObjectInvoker invoker, Class<?>... proxyClasses);
}
