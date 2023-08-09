package org.idea.srpc.core.common.wrapper;

import java.io.Serializable;

/**
 * 包装RPC请求的具体数据
 */
public class RpcInvocation implements Serializable {

    private static final long serialVersionUID = -4051403147094039682L;

    /**
     * 每一次请求应该使用唯一的uuid进行标识
     */
    private String uuid;

    /**
     * 目标服务名
     */
    private String targetServiceName;

    /**
     * 目标方法名
     */
    private String targetMethodName;

    /**
     * 方法执行参数
     */
    private Object[] args;

    /**
     * 方法返回的结果
     */
    private Object response;

    /**
     * 方法产生的异常
     */
    private Throwable e;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public String getTargetMethodName() {
        return targetMethodName;
    }

    public void setTargetMethodName(String targetMethodName) {
        this.targetMethodName = targetMethodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
