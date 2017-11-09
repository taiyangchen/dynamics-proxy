package com.sm.common.libs.dynproxy;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.Builder;

import com.sm.common.libs.dynproxy.Invocation;
import com.sm.common.libs.dynproxy.util.ProxyUtil;

/**
 * AbstractTestCase for proxy
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:19:18
 */
public abstract class AbstractTest {

  protected void assertSerializable(Object o) {
    assertTrue(o instanceof Serializable);
    SerializationUtils.clone((Serializable) o);
  }

  protected MockInvocationBuilder mockInvocation(Class<?> type, String name, Class<?>... argumentTypes) {
    try {
      return new MockInvocationBuilder(Validate.notNull(type).getMethod(name, argumentTypes));
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException("Method not found.", e);
    }
  }

  protected static final class MockInvocationBuilder implements Builder<Invocation> {
    private final Method method;
    private Object[] arguments = ProxyUtil.EMPTY_ARGUMENTS;
    private Object returnValue = null;

    public MockInvocationBuilder(Method method) {
      this.method = method;
    }

    public MockInvocationBuilder withArguments(Object... arguments) {
      this.arguments = arguments;
      return this;
    }

    public MockInvocationBuilder returning(Object value) {
      this.returnValue = value;
      return this;
    }

    @Override
    public Invocation build() {
      return new MockInvocation(method, returnValue, arguments);
    }
  }
}
