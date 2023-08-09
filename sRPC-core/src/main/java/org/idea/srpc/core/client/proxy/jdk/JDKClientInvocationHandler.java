package org.idea.srpc.core.client.proxy.jdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.idea.srpc.core.common.wrapper.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.idea.srpc.core.common.cache.CommonClientCache.*;

public class JDKClientInvocationHandler implements InvocationHandler {
    private static final Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * RPC请求实际调用的逻辑，需要将RPC请求封包发送
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 封装RPC请求
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setUuid(UUID.randomUUID().toString());
        rpcInvocation.setTargetServiceName(clazz.getName());
        rpcInvocation.setTargetMethodName(method.getName());
        rpcInvocation.setArgs(args);
        RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        // 发送RPC请求，如果直接在当前主线程处理发送事件，会有啥问题
        // todo 如何将同步RPC请求，改成异步RPC请求，或者支持callback
        // todo 这里有过期时间，服务端是否也需要添加一个过期时间
        SEND_QUEUE.add(rpcInvocation);
        long beginTime = System.currentTimeMillis();
        // 等待请求返回的时间
        while (System.currentTimeMillis() - beginTime < 30000) {
            Object object = RESP_MAP.get(rpcInvocation.getUuid());
            if (object instanceof RpcInvocation) {
                RESP_MAP.remove(rpcInvocation.getUuid());
                RpcInvocation respRpcInvocation = (RpcInvocation) object;
                if (method.getReturnType().equals(Void.TYPE)) {
                    return null;
                }
                // 对于fastjson单独处理
                if (CLIENT_CONFIG.getClientSerialize().equals("fastjson")) {
                    return CLIENT_SERIALIZE_FACTORY.deserialize(CLIENT_SERIALIZE_FACTORY
                            .serialize(respRpcInvocation.getResponse()), method.getReturnType());

                }
                return respRpcInvocation.getResponse();
            }
        }
        RESP_MAP.remove(rpcInvocation.getUuid());
        throw new TimeoutException("client wait server's response timeout!");
    }
}
