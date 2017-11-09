package com.sm.common.libs.dynproxy.sample;

import com.sm.common.libs.dynproxy.Interceptor;
import com.sm.common.libs.dynproxy.Invocation;

/**
 * SuffixInterceptor
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月26日 下午6:40:01
 */
public class SuffixInterceptor implements Interceptor {

  private static final long serialVersionUID = 2275465822166741737L;

  private final String suffix;

  public SuffixInterceptor(String suffix) {
    this.suffix = suffix;
  }

  @Override
  public Object intercept(Invocation methodInvocation) throws Throwable {
    Object result = methodInvocation.proceed();
    if (result instanceof String) {
      result = ((String) result) + suffix;
    }

    return result;
  }
}
