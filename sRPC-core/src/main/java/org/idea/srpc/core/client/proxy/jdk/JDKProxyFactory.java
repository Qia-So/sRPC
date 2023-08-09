package org.idea.srpc.core.client.proxy.jdk;

import org.idea.srpc.core.client.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new JDKClientInvocationHandler(clazz));
    }
}
