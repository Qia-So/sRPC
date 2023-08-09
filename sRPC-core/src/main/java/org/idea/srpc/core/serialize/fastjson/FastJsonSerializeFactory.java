package org.idea.srpc.core.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import org.idea.srpc.core.serialize.SerializeFactory;

public class FastJsonSerializeFactory implements SerializeFactory {
    @Override
    public <T> byte[] serialize(T t) {
        String json = JSON.toJSONString(t);
        return json.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        String json = new String(bytes);
        T t = JSON.parseObject(json, clazz);
        return t;
    }
}
