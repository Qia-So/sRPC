package org.idea.srpc.core.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.idea.srpc.core.serialize.SerializeFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializeFactory implements SerializeFactory {
    private static final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            return kryo;
        }
    };

    @Override
    public <T> byte[] serialize(T t) {
        Output output = null;
        try {
            Kryo kryo = kryos.get();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            output = new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Input input = null;
        try {
            Kryo kryo = kryos.get();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            input = new Input(byteArrayInputStream);
            Object object = kryo.readClassAndObject(input);
            return ((T) object);
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
