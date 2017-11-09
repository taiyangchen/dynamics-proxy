package com.sm.common.libs.dynproxy.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.sm.common.libs.dynproxy.Interceptor;
import com.sm.common.libs.dynproxy.Invocation;
import com.sm.common.libs.dynproxy.ObjectInvoker;
import com.sm.common.libs.dynproxy.ObjectProvider;
import com.sm.common.libs.dynproxy.template.CreatorTemplate;
import com.sm.common.libs.dynproxy.util.ProxyUtil;

/**
 * JDK动态代理创建者
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2015-3-9 下午11:00:42
 */
public class JdkProxyCreator extends CreatorTemplate {

  @Override
  public <T> T createDelegatorProxy(ClassLoader classLoader, ObjectProvider<?> delegateProvider,
      Class<?>... proxyClasses) {
    @SuppressWarnings("unchecked")
    T result = (T) Proxy.newProxyInstance(classLoader, proxyClasses, new DelegatorInvocationHandler(delegateProvider));

    return result;
  }

  @Override
  public <T> T createInterceptorProxy(ClassLoader classLoader, Object target, Interceptor interceptor,
      Class<?>... proxyClasses) {
    @SuppressWarnings("unchecked")
    T result =
        (T) Proxy.newProxyInstance(classLoader, proxyClasses, new InterceptorInvocationHandler(target, interceptor));

    return result;
  }

  @Override
  public <T> T createInvokerProxy(ClassLoader classLoader, ObjectInvoker invoker, Class<?>... proxyClasses) {
    @SuppressWarnings("unchecked")
    T result = (T) Proxy.newProxyInstance(classLoader, proxyClasses, new InvokerInvocationHandler(invoker));

    return result;
  }

  /**
   * 抽象的<code>InvocationHandler</code>
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午6:25:19
   */
  private abstract static class AbstractInvocationHandler implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -5735923917525983672L;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (ProxyUtil.isHashCode(method)) {
        return Integer.valueOf(System.identityHashCode(proxy));
      }

      if (ProxyUtil.isEqualsMethod(method)) {
        return Boolean.valueOf(proxy == args[0]);
      }

      return doInvoke(proxy, method, args);
    }

    /**
     * 调用实现
     * 
     * @param proxy 代理对象
     * @param method 方法
     * @param args 方法参数
     * @return 调用结果
     * @throws Throwable
     */
    protected abstract Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable;
  }

  /**
   * 委托的<code>InvocationHandler</code>
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午6:26:15
   */
  private static class DelegatorInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = -7039084673621066448L;

    private final ObjectProvider<?> delegateProvider;

    protected DelegatorInvocationHandler(ObjectProvider<?> delegateProvider) {
      this.delegateProvider = delegateProvider;
    }

    @Override
    public Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
      try {
        return method.invoke(delegateProvider.getObject(), args);
      } catch (InvocationTargetException e) {
        throw e.getTargetException();
      }
    }
  }

  /**
   * 拦截的<code>InvocationHandler</code>
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午6:26:33
   */
  private static class InterceptorInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = -5079758027748139728L;

    private final Object target;
    private final Interceptor methodInterceptor;

    public InterceptorInvocationHandler(Object target, Interceptor methodInterceptor) {
      this.target = target;
      this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
      ReflectionInvocation invocation = new ReflectionInvocation(proxy, target, method, args);
      return methodInterceptor.intercept(invocation);
    }
  }

  /**
   * 直接调用的<code>InvocationHandler</code>
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午6:26:46
   */
  private static class InvokerInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = 4277344818056348876L;

    private final ObjectInvoker invoker;

    public InvokerInvocationHandler(ObjectInvoker invoker) {
      this.invoker = invoker;
    }

    @Override
    public Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
      return invoker.invoke(proxy, method, args);
    }
  }

  /**
   * 反射调用器
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午6:26:58
   */
  private static class ReflectionInvocation implements Invocation {
    private final Object proxy;
    private final Object target;
    private final Method method;
    private final Object[] arguments;

    public ReflectionInvocation(Object proxy, Object target, Method method, Object[] arguments) {
      this.proxy = proxy;
      this.target = target;
      this.method = method;
      this.arguments = ObjectUtils.defaultIfNull(ArrayUtils.clone(arguments), ProxyUtil.EMPTY_ARGUMENTS);
    }

    @Override
    public Object[] getArguments() {
      return arguments;
    }

    @Override
    public Method getMethod() {
      return method;
    }

    @Override
    public Object getProxy() {
      return proxy;
    }

    @Override
    public Object proceed() throws Throwable {
      try {
        return method.invoke(target, arguments);
      } catch (InvocationTargetException e) {
        throw e.getTargetException();
      }
    }
  }
}
