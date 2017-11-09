package com.sm.common.libs.dynproxy.exception;

import com.sm.common.libs.exception.MessageRuntimeException;
import com.sm.common.libs.util.MessageUtil;

/**
 * 动态代理创建异常
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:00:29
 */
public class ProxyCreatorException extends MessageRuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -5278506190465957728L;

  /**
   * 构造一个空的异常.
   */
  public ProxyCreatorException() {
    super();
  }

  /**
   * 构造一个异常, 指明异常的详细信息.
   * 
   * @param message 详细信息
   */
  public ProxyCreatorException(String message) {
    super(message);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param cause 异常的起因
   */
  public ProxyCreatorException(Throwable cause) {
    super(cause);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   */
  public ProxyCreatorException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 构造一个异常, 参数化详细信息
   * 
   * @param message 详细信息
   * @param params 参数表
   */
  public ProxyCreatorException(String message, Object... params) {
    super(MessageUtil.formatLogMessage(message, params));
  }

  /**
   * 构造一个异常, 参数化详细信息,指明引起这个异常的起因
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   * @param params 参数表
   */
  public ProxyCreatorException(String message, Throwable cause, Object... params) {
    super(MessageUtil.formatLogMessage(message, params), cause);
  }

}
