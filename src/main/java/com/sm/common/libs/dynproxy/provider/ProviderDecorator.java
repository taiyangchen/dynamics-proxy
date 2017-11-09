package com.sm.common.libs.dynproxy.provider;

import com.sm.common.libs.dynproxy.ObjectProvider;

/**
 * <code>ObjectProvider</code>装饰器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:24:45
 * @param <T>
 */
public class ProviderDecorator<T> implements ObjectProvider<T> {

  /**
   * 
   */
  private static final long serialVersionUID = 6927825345741706969L;

  private ObjectProvider<? extends T> inner;

  public ProviderDecorator(ObjectProvider<? extends T> inner) {
    this.inner = inner;
  }

  @Override
  public T getObject() {
    return inner.getObject();
  }

  protected ObjectProvider<? extends T> getInner() {
    return inner;
  }

  public void setInner(ObjectProvider<? extends T> inner) {
    this.inner = inner;
  }
}
