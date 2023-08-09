package org.idea.srpc.core.common.cache;

import org.idea.srpc.core.common.config.ServerConfig;
import org.idea.srpc.core.serialize.SerializeFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 将服务端需要用到的公共缓存抽取出来
 */
public class CommonServerCache {
    /**
     * 需要在服务端注册对应的服务
     */
    public static final Map<String, Object> PROVIDER_CLASS_MAP = new HashMap<>();
    /**
     * 序列化方式
     */
    public static SerializeFactory SERVER_SERIALIZE_FACTORY;
    /**
     * 服务端配置信息
     */
    public static ServerConfig SERVER_CONFIG;

}
