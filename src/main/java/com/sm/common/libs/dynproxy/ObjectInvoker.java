package com.sm.common.libs.dynproxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 对象调用接口
 * <p>
 * 麻痹，因maven编译器不通过改名字
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:53:17
 */
public interface ObjectInvoker extends Serializable {

  /**
   * 方法调用
   * 
   * @param proxy 代理对象
   * @param method 调用方法
   * @param arguments 调用方法参数
   * @return 调用结果
   * @throws Throwable
   */
  Object invoke(Object proxy, Method method, Object... arguments) throws Throwable;

}
