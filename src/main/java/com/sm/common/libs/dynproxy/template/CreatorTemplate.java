package com.sm.common.libs.dynproxy.template;

import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.dynproxy.Interceptor;
import com.sm.common.libs.dynproxy.ObjectInvoker;
import com.sm.common.libs.dynproxy.ObjectProvider;
import com.sm.common.libs.dynproxy.ProxyCreator;

/**
 * 代理类创建者模版
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:58:20
 */
public abstract class CreatorTemplate extends LoggerSupport implements ProxyCreator {

  @Override
  public boolean canProxy(Class<?>... proxyClasses) {
    for (Class<?> proxyClass : proxyClasses) {
      if (!proxyClass.isInterface()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public <T> T createDelegatorProxy(ObjectProvider<?> delegateProvider, Class<?>... proxyClasses) {
    return createDelegatorProxy(Thread.currentThread().getContextClassLoader(), delegateProvider, proxyClasses);
  }

  @Override
  public <T> T createInterceptorProxy(Object target, Interceptor interceptor, Class<?>... proxyClasses) {
    return createInterceptorProxy(Thread.currentThread().getContextClassLoader(), target, interceptor, proxyClasses);
  }

  @Override
  public <T> T createInvokerProxy(ObjectInvoker invoker, Class<?>... proxyClasses) {
    return createInvokerProxy(Thread.currentThread().getContextClassLoader(), invoker, proxyClasses);
  }

}
