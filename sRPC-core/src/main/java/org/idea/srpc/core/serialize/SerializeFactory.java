package org.idea.srpc.core.serialize;

/**
 * 序列化工厂，提供序列化和反序列化方法
 */
public interface SerializeFactory {
    <T> byte[] serialize(T t);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
