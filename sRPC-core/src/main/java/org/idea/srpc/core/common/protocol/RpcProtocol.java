package org.idea.srpc.core.common.protocol;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RPC协议，定义数据的发送接收规则
 */
public class RpcProtocol implements Serializable {

    private static final long serialVersionUID = -5131715886177789426L;

    /**
     * RPC请求数据的实际长度
     */
    private int contentLength;

    /**
     * RPC请求的字节数据
     */
    private byte[] content;

    public RpcProtocol(byte[] content) {
        this.content = content;
        this.contentLength = content.length;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
