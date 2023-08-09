package org.idea.srpc.core.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.idea.srpc.core.common.protocol.RpcProtocol;
import org.idea.srpc.core.common.wrapper.RpcInvocation;

/**
 * 编码器，将RpcProtocol对象转成字节发送出去
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
    }
}
