package org.idea.srpc.core.serialize.jdk;

import org.idea.srpc.core.serialize.SerializeFactory;

import java.io.*;

public class JDKSerializeFactory implements SerializeFactory {
    @Override
    public <T> byte[] serialize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            oos.flush();
            oos.close();
            data = bos.toByteArray();
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            bis.close();
            ois.close();
            return ((T) object);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
