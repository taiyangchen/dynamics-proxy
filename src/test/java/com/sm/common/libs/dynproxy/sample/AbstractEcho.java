package com.sm.common.libs.dynproxy.sample;

import java.io.Serializable;

/**
 * AbstractEcho
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月26日 下午6:39:18
 */
public abstract class AbstractEcho implements Echo, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1183412961644181457L;

  @Override
  public String echoBack(String message) {
    return message;
  }
}
