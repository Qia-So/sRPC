package org.idea.srpc.core.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.idea.srpc.core.common.protocol.RpcProtocol;
import org.idea.srpc.core.common.wrapper.RpcInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.idea.srpc.core.common.cache.CommonClientCache.*;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        byte[] responseContent = rpcProtocol.getContent();
        RpcInvocation rpcInvocation = CLIENT_SERIALIZE_FACTORY.deserialize(responseContent, RpcInvocation.class);
        // 接收到没有记录的uuid，假如已经过期，则会从RESP_MAP中删除uuid，这里自然也会抛出异常
        if (!RESP_MAP.containsKey(rpcInvocation.getUuid())) {
            throw new IllegalArgumentException("server response is error!");
        }
        RESP_MAP.put(rpcInvocation.getUuid(), rpcInvocation);
        // 查看这里是否成功将引用计数-1
//        boolean release = ReferenceCountUtil.release(msg);
//        LOGGER.info("释放msg结果：{}", release); 结果为false，说明没有必要
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        if (ctx.channel().isActive()) {
            ctx.close();
        }
    }
}
