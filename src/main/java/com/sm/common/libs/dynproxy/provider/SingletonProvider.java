package com.sm.common.libs.dynproxy.provider;

import com.sm.common.libs.dynproxy.ObjectProvider;

/**
 * SingletonProvider
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:24:56
 * @param <T>
 */
public class SingletonProvider<T> extends ProviderDecorator<T> {

  /**
   * 
   */
  private static final long serialVersionUID = -3715549852313953689L;

  private T instance;

  public SingletonProvider(ObjectProvider<? extends T> inner) {
    super(inner);

    instance = super.getObject();
    setInner(null);
  }

  @Override
  public T getObject() {
    return instance;
  }
}
