package org.idea.srpc.core.client;

import bean.User;
import com.alibaba.fastjson.JSON;
import interfaces.user.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.idea.srpc.core.client.proxy.jdk.JDKProxyFactory;
import org.idea.srpc.core.common.config.ClientConfig;
import org.idea.srpc.core.common.handler.RpcDecoder;
import org.idea.srpc.core.common.handler.RpcEncoder;
import org.idea.srpc.core.common.protocol.RpcProtocol;
import org.idea.srpc.core.common.wrapper.RpcInvocation;
import org.idea.srpc.core.serialize.fastjson.FastJsonSerializeFactory;
import org.idea.srpc.core.serialize.jdk.JDKSerializeFactory;
import org.idea.srpc.core.serialize.kryo.KryoSerializeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.idea.srpc.core.common.cache.CommonClientCache.*;
import static org.idea.srpc.core.common.constants.SerializeConstants.*;

public class Client {
    private Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static EventLoopGroup clientGroup = new NioEventLoopGroup();

    /**
     * 与服务端进行连接，同时开启发送RPC请求的任务
     * @return 返回一个Rpc服务代理工厂
     * @throws InterruptedException
     */
    public RpcServiceProxyFactory startClientApplication() throws InterruptedException {
        clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RpcEncoder());
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(CLIENT_CONFIG.getServerAddr(), CLIENT_CONFIG.getPort()).sync();
        LOGGER.info("====================服务启动====================");
        startSendJob(channelFuture);
        // 返回一个Rpc服务代理工厂，指定创建代理对象的具体工厂
        RpcServiceProxyFactory rpcServiceProxyFactory = new RpcServiceProxyFactory(new JDKProxyFactory());
        return rpcServiceProxyFactory;
    }

    /**
     * 开启一个异步发送任务的线程
     * @param channelFuture
     */
    public void startSendJob(ChannelFuture channelFuture) {
        new Thread(new AsyncSendJob(channelFuture), "Thread-SendJob").start();
    }
    /**
     * 发送RPC请求的任务
     */
    class AsyncSendJob implements Runnable {
        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    RpcInvocation rpcInvocation = SEND_QUEUE.take();
                    RpcProtocol rpcProtocol = new RpcProtocol(CLIENT_SERIALIZE_FACTORY.serialize(rpcInvocation));
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void initClientConfig() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setServerAddr("localhost");
        clientConfig.setPort(7144);
        clientConfig.setClientSerialize("kryo");
        CLIENT_CONFIG = clientConfig;
        String clientSerialize = CLIENT_CONFIG.getClientSerialize();
        switch (clientSerialize) {
            case JDK_SERIALIZE_TYPE:
                CLIENT_SERIALIZE_FACTORY = new JDKSerializeFactory();
                break;
            case FASTJSON_SERIALIZE_TYPE:
                CLIENT_SERIALIZE_FACTORY = new FastJsonSerializeFactory();
                break;
            case KRYO_SERIALIZE_TYPE:
                CLIENT_SERIALIZE_FACTORY = new KryoSerializeFactory();
                break;
            default:
                LOGGER.warn("not support serialize type: {}, use the default serialize type: {}", clientSerialize, KRYO_SERIALIZE_TYPE);
                CLIENT_SERIALIZE_FACTORY = new KryoSerializeFactory();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.initClientConfig();
        RpcServiceProxyFactory rpcServiceProxyFactory = client.startClientApplication();
        UserService userService = rpcServiceProxyFactory.get(UserService.class);
        long beginTime = System.currentTimeMillis();
        List<User> allUsers = userService.getAllUsers();
        System.out.println("RPC请求调用结果：" + allUsers);
        System.out.println("===============================");
        for (int i = 1; i <= 10; i++) {
            User userById = userService.getUserById(i);
            System.out.println("RPC请求调用结果：" + userById);
        }
        System.out.println("===============================");
        for (int i = 1; i <= 10; i++) {
            userService.updateUserStatusById(i);
        }
        System.out.println("RPC请求更新调用结果：" + userService.getAllUsers());
        System.out.println("RPC请求用时：" + (System.currentTimeMillis() - beginTime) + "ms");
    }
}
