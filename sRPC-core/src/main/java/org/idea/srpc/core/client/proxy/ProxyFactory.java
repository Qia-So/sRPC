package org.idea.srpc.core.client.proxy;

public interface ProxyFactory {
    <T> T getProxy(final Class<T> clazz);
}
