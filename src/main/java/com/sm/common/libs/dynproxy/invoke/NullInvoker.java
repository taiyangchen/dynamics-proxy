package com.sm.common.libs.dynproxy.invoke;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.sm.common.libs.dynproxy.ObjectInvoker;
import com.sm.common.libs.dynproxy.util.ProxyUtil;

/**
 * 代表“NULL”的Invoker
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:28:13
 */
public class NullInvoker implements ObjectInvoker, Serializable {

  private static final long serialVersionUID = 4430908314204545174L;

  public static final NullInvoker INSTANCE = new NullInvoker();

  @Override
  public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
    Class<?> returnType = method.getReturnType();

    return ProxyUtil.nullValue(returnType);
  }
}
