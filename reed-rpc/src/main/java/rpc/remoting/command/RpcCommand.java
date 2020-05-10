package rpc.remoting.command;

import protocol.ProtocolType;
import rpc.remoting.InvokeContext;
import lombok.Data;
import serialzation.Serializer;

import java.io.Serializable;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:12
 **/
@Data
public abstract class RpcCommand implements Serializable {
    protected int            id;
    protected RpcCommandType type;
    protected InvokeContext  invokeContext;
    protected ProtocolType   protocolType;
    protected Serializer     serializer;
    protected byte           classLen;
    protected byte           headerLen;
    protected int            ContentLen;
    protected byte[]         className;
    protected byte[]         head;
    protected byte[]         content;


    public abstract void serialize();

    public abstract void deserialize();

}
