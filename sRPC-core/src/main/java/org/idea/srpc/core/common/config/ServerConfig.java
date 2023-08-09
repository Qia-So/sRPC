package org.idea.srpc.core.common.config;

/**
 * 将服务端可能自定义的配置抽取出来
 */
public class ServerConfig {
    private int serverPort; // 监听的端口
    private String serverSerialize; // 序列化方式

    public String getServerSerialize() {
        return serverSerialize;
    }

    public void setServerSerialize(String serverSerialize) {
        this.serverSerialize = serverSerialize;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
