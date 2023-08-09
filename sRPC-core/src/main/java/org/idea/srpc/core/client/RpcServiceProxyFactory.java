package org.idea.srpc.core.client;

import org.idea.srpc.core.client.proxy.ProxyFactory;

public class RpcServiceProxyFactory {
    private ProxyFactory proxyFactory;

    public RpcServiceProxyFactory(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public <T> T get(Class<T> tClass) {
        return proxyFactory.getProxy(tClass);
    }
}
