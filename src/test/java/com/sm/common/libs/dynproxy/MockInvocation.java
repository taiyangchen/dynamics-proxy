package com.sm.common.libs.dynproxy;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.sm.common.libs.dynproxy.Invocation;
import com.sm.common.libs.dynproxy.util.ProxyUtil;

/**
 * MockInvocation
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:20:53
 */
public class MockInvocation implements Invocation {

  private final Method method;
  private final Object[] arguments;
  private final Object returnValue;

  public MockInvocation(Method method, Object returnValue, Object... arguments) {
    this.returnValue = returnValue;
    this.arguments = ObjectUtils.defaultIfNull(ArrayUtils.clone(arguments), ProxyUtil.EMPTY_ARGUMENTS);
    this.method = method;
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
    return null;
  }

  @Override
  public Object proceed() throws Throwable {
    return returnValue;
  }
}
