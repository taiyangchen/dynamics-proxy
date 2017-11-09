package com.sm.common.libs.dynproxy.sample;

import java.io.IOException;

/**
 * Echo
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月26日 下午6:39:35
 */
public interface Echo {

  void echo();

  String echoBack(String message);

  String echoBack(String[] messages);

  String echoBack(String[][] messages);

  String echoBack(String[][][] messages);

  int echoBack(int i);

  boolean echoBack(boolean b);

  String echoBack(String message1, String message2);

  void illegalArgument();

  void ioException() throws IOException;

}
