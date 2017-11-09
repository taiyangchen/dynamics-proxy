package com.sm.common.libs.dynproxy.invoke;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.sm.common.libs.dynproxy.ObjectInvoker;

/**
 * 用于返回<code>null</code>Invoker
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:28:03
 */
public class NoneInvoker implements ObjectInvoker, Serializable {

  private static final long serialVersionUID = -6264105341970426080L;

  @Override
  public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
    return null;
  }

}
