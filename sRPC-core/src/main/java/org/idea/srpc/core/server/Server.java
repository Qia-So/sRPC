package org.idea.srpc.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.idea.srpc.core.common.config.ServerConfig;
import org.idea.srpc.core.common.handler.RpcDecoder;
import org.idea.srpc.core.common.handler.RpcEncoder;
import org.idea.srpc.core.serialize.fastjson.FastJsonSerializeFactory;
import org.idea.srpc.core.serialize.jdk.JDKSerializeFactory;
import org.idea.srpc.core.serialize.kryo.KryoSerializeFactory;
import org.idea.srpc.core.server.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.idea.srpc.core.common.cache.CommonClientCache.CLIENT_SERIALIZE_FACTORY;
import static org.idea.srpc.core.common.cache.CommonServerCache.*;
import static org.idea.srpc.core.common.constants.SerializeConstants.*;
public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    // 用来接收连接的线程组
    private static EventLoopGroup bossGroup = null;
    // 用来处理读写的线程组
    private static EventLoopGroup workerGroup = null;

    // 开启服务器
    public void startApplication() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_SNDBUF, 16*1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024) // 设置在ServerSocket上才有效
                .childOption(ChannelOption.TCP_NODELAY, true) // 禁用nagle算法
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        bootstrap.bind(SERVER_CONFIG.getServerPort()).sync();
    }

    /**
     * 注册服务 todo 验证能否注册类对象，经验证，使用反射指定方法调用时，需要指定实例对象
     * @param serviceBean 某个服务实现类的类对象
     */
    public void registryService(Object serviceBean) {
        Class<?>[] interfaces = serviceBean.getClass().getInterfaces();
        if (interfaces.length == 0) {
            LOGGER.error("happens in Server#registryServiceservice, cause: service must have a interface");
            throw new RuntimeException("service must have a interface");
        }
        if (interfaces.length > 1) {
            LOGGER.error("happens in Server#registryServiceservice, cause: service must only have a interface\"");
            throw new RuntimeException("service must only have a interface");
        }
        Class<?> service = interfaces[0];
        PROVIDER_CLASS_MAP.put(service.getName(), serviceBean);
    }
    private void initServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServerPort(7144);
        serverConfig.setServerSerialize("kryo");
        SERVER_CONFIG = serverConfig;
        String serverSerialize = SERVER_CONFIG.getServerSerialize();
        switch (serverSerialize) {
            case JDK_SERIALIZE_TYPE:
                SERVER_SERIALIZE_FACTORY = new JDKSerializeFactory();
                break;
            case FASTJSON_SERIALIZE_TYPE:
                SERVER_SERIALIZE_FACTORY = new FastJsonSerializeFactory();
                break;
            case KRYO_SERIALIZE_TYPE:
                SERVER_SERIALIZE_FACTORY = new KryoSerializeFactory();
                break;
            default:
                LOGGER.warn("not support serialize type: {}, use the default serialize type: {}", serverSerialize, KRYO_SERIALIZE_TYPE);
                CLIENT_SERIALIZE_FACTORY = new KryoSerializeFactory();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        server.initServerConfig();
        server.registryService(new UserServiceImpl());
        server.startApplication();
    }
}
