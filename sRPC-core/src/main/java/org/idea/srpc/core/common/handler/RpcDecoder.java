package org.idea.srpc.core.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.idea.srpc.core.common.protocol.RpcProtocol;

import java.util.List;

/**
 * 解码器，接收字节，恢复成RpcProtocol对象
 */
public class RpcDecoder extends ByteToMessageDecoder {
    private final int BASE_LENGTH = 4; // 长度为int，所以至少有四个字节
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= BASE_LENGTH) {
            in.markReaderIndex(); // 标记一下
            int contentLength = in.readInt();
            // 如果可读字节数 小于 应该收到的字节数，代表还有未接收的数据，重置读索引，等待下一次再读
            if (in.readableBytes() < contentLength) {
                in.resetReaderIndex();
                return;
            }
            byte[] data = new byte[contentLength];
            in.readBytes(data);
            RpcProtocol rpcProtocol = new RpcProtocol(data);
            out.add(rpcProtocol);
        }
    }
}
