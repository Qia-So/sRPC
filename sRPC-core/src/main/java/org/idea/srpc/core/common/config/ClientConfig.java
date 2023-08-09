package org.idea.srpc.core.common.config;

public class ClientConfig {
    // 服务端地址
    private String serverAddr;
    // 服务端的端口
    private Integer port;
    // 序列化方式
    private String clientSerialize;

    public String getClientSerialize() {
        return clientSerialize;
    }

    public void setClientSerialize(String clientSerialize) {
        this.clientSerialize = clientSerialize;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
