package com.sm.common.libs.dynproxy.provider;

import java.io.Serializable;

import com.sm.common.libs.dynproxy.ObjectProvider;

/**
 * Bean对象提供者
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:24:18
 * @param <T>
 */
public class BeanProvider<T> implements ObjectProvider<T>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6261791550469264285L;

  private final Class<? extends T> beanClass;

  public BeanProvider(Class<? extends T> beanClass) {
    this.beanClass = beanClass;
  }

  @Override
  public T getObject() {
    try {
      return beanClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
  }
}
