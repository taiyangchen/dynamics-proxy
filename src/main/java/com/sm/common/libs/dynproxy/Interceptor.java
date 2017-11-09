package com.sm.common.libs.dynproxy;

import java.io.Serializable;

/**
 * 定义拦截器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:52:30
 */
public interface Interceptor extends Serializable {

  /**
   * 方法拦截
   * 
   * @param invocation 调用接口
   * @return 拦截后的结果
   * @throws Throwable
   */
  Object intercept(Invocation invocation) throws Throwable;

}
