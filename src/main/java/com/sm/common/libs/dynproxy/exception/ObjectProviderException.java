package com.sm.common.libs.dynproxy.exception;

import com.sm.common.libs.exception.MessageRuntimeException;
import com.sm.common.libs.util.MessageUtil;

/**
 * 对象提供异常
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:00:22
 */
public class ObjectProviderException extends MessageRuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -5766035082307815151L;

  /**
   * 构造一个空的异常.
   */
  public ObjectProviderException() {
    super();
  }

  /**
   * 构造一个异常, 指明异常的详细信息.
   * 
   * @param message 详细信息
   */
  public ObjectProviderException(String message) {
    super(message);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param cause 异常的起因
   */
  public ObjectProviderException(Throwable cause) {
    super(cause);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   */
  public ObjectProviderException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 构造一个异常, 参数化详细信息
   * 
   * @param message 详细信息
   * @param params 参数表
   */
  public ObjectProviderException(String message, Object... params) {
    super(MessageUtil.formatLogMessage(message, params));
  }

  /**
   * 构造一个异常, 参数化详细信息,指明引起这个异常的起因
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   * @param params 参数表
   */
  public ObjectProviderException(String message, Throwable cause, Object... params) {
    super(MessageUtil.formatLogMessage(message, params), cause);
  }

}
