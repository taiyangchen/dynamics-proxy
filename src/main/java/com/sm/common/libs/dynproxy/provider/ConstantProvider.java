package com.sm.common.libs.dynproxy.provider;

import java.io.Serializable;

import com.sm.common.libs.dynproxy.ObjectProvider;

/**
 * 常量对象提供者
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:24:29
 * @param <T>
 */
public class ConstantProvider<T> implements ObjectProvider<T>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8677993768843722265L;

  private final T constant;

  public ConstantProvider(T constant) {
    this.constant = constant;
  }

  @Override
  public T getObject() {
    return constant;
  }
}
