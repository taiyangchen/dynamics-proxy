package com.sm.common.libs.dynproxy;

import java.io.Serializable;

/**
 * 对象提供者
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:53:27
 * @param <T>
 */
public interface ObjectProvider<T> extends Serializable {

  /**
   * 获取对象
   * 
   * @return
   */
  T getObject();
}
