package org.idea.srpc.core.common.cache;

import org.idea.srpc.core.common.config.ClientConfig;
import org.idea.srpc.core.common.wrapper.RpcInvocation;
import org.idea.srpc.core.serialize.SerializeFactory;
import org.idea.srpc.core.serialize.fastjson.FastJsonSerializeFactory;
import org.idea.srpc.core.serialize.jdk.JDKSerializeFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CommonClientCache {
    // 阻塞队列，用来异步发送RPC请求
    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue<>(100);
    // 用来接收RPC请求返回的结果
    public static Map<String, Object> RESP_MAP = new HashMap<>();
    // 序列化方式
    public static SerializeFactory CLIENT_SERIALIZE_FACTORY;
    // 客户端配置信息
    public static ClientConfig CLIENT_CONFIG;

}
