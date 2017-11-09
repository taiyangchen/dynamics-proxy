package com.sm.common.libs.dynproxy;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 方法签名
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午3:57:46
 */
public class MethodSignature implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2236251542533774653L;

  /**
   * 基本类型对应的方法签名的MAP
   */
  private static final Map<Class<?>, Character> primitiveAbbreviations;

  /**
   * 方法签名对应的基本类型的MAP
   */
  private static final Map<Character, Class<?>> reverseAbbreviations;

  static {
    Map<Class<?>, Character> map = new HashMap<Class<?>, Character>();

    map.put(Boolean.TYPE, Character.valueOf('Z'));
    map.put(Byte.TYPE, Character.valueOf('B'));
    map.put(Short.TYPE, Character.valueOf('S'));
    map.put(Integer.TYPE, Character.valueOf('I'));
    map.put(Character.TYPE, Character.valueOf('C'));
    map.put(Long.TYPE, Character.valueOf('J'));
    map.put(Float.TYPE, Character.valueOf('F'));
    map.put(Double.TYPE, Character.valueOf('D'));
    map.put(Void.TYPE, Character.valueOf('V'));

    Map<Character, Class<?>> reverseMap = new HashMap<Character, Class<?>>();
    for (Map.Entry<Class<?>, Character> entry : map.entrySet()) {
      reverseMap.put(entry.getValue(), entry.getKey());
    }

    primitiveAbbreviations = Collections.unmodifiableMap(map);
    reverseAbbreviations = Collections.unmodifiableMap(reverseMap);
  }

  /**
   * 添加方法签名到字符串缓冲
   * 
   * @param buf 字符串缓冲
   * @param type Class类型
   */
  private static void appendTo(StringBuilder buf, Class<?> type) {
    if (type.isPrimitive()) {
      buf.append(primitiveAbbreviations.get(type));
    } else if (type.isArray()) {
      buf.append('[');
      appendTo(buf, type.getComponentType());
    } else {
      buf.append('L').append(type.getName().replace('.', '/')).append(';');
    }
  }

  /**
   * 方法签名位移
   * 
   * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
   * @version create on 2016年11月26日 下午5:55:04
   */
  private static class SignaturePosition extends ParsePosition {
    SignaturePosition() {
      super(0);
    }

    SignaturePosition next() {
      return plus(1);
    }

    SignaturePosition plus(int addend) {
      setIndex(getIndex() + addend);
      return this;
    }
  }

  /**
   * 解析内部字符串表达式为<code>Pair</code>
   * 
   * @param internal 内部字符串表达式
   * @return <code>Pair</code>对象 @see Pair
   */
  private static Pair<String, Class<?>[]> parse(String internal) {
    SignaturePosition pos = new SignaturePosition();
    int lparen = internal.indexOf('(', pos.getIndex());

    String name = internal.substring(0, lparen).trim();

    pos.setIndex(lparen + 1);

    List<Class<?>> params = new ArrayList<Class<?>>();
    while (pos.getIndex() < internal.length()) {
      char c = internal.charAt(pos.getIndex());
      if (Character.isWhitespace(c)) {
        pos.next();
        continue;
      }
      Character k = Character.valueOf(c);
      if (reverseAbbreviations.containsKey(k)) {
        params.add(reverseAbbreviations.get(k));
        pos.next();
        continue;
      }
      if (')' == c) {
        pos.next();
        break;
      }
      try {
        params.add(parseType(internal, pos));
      } catch (ClassNotFoundException e) {
        throw new IllegalArgumentException(String.format("Method signature \"%s\" references unknown type", internal),
            e);
      }
    }

    return Pair.of(name, params.toArray(ArrayUtils.EMPTY_CLASS_ARRAY));
  }

  /**
   * 根据<code>internal</code>和方法签名位移，解析成对应类型
   * 
   * @param internal 内部字符串表达式
   * @param pos 方法签名位移 @see SignaturePosition
   * @return 对应类型
   * @throws ClassNotFoundException
   */
  private static Class<?> parseType(String internal, SignaturePosition pos) throws ClassNotFoundException {
    int here = pos.getIndex();
    char c = internal.charAt(here);

    switch (c) {
      case '[':
        pos.next();
        Class<?> componentType = parseType(internal, pos);
        return Array.newInstance(componentType, 0).getClass();
      case 'L':
        pos.next();
        int type = pos.getIndex();
        int semi = internal.indexOf(';', type);

        String className = internal.substring(type, semi).replace('/', '.');

        pos.setIndex(semi + 1);
        return Class.forName(className);
      default:
        throw new IllegalArgumentException(String.format("Unexpected character at index %d of method signature \"%s\"",
            Integer.valueOf(here), internal));
    }
  }

  private final String internal;

  public MethodSignature(Method method) {
    final StringBuilder buf = new StringBuilder(method.getName()).append('(');
    for (Class<?> p : method.getParameterTypes()) {
      appendTo(buf, p);
    }

    buf.append(')');
    this.internal = buf.toString();
  }

  public Method toMethod(Class<?> type) {
    final Pair<String, Class<?>[]> info = parse(internal);
    return MethodUtils.getAccessibleMethod(type, info.getLeft(), info.getRight());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }

    if (o.getClass() != getClass()) {
      return false;
    }

    MethodSignature other = (MethodSignature) o;
    return other.internal.equals(internal);
  }

  @Override
  public int hashCode() {
    return internal.hashCode();
  }

  @Override
  public String toString() {
    return internal;
  }
}
