package rpc.remoting.command;

import protocol.ProtocolType;
import rpc.remoting.InvokeContext;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:12
 **/
@Data
public abstract class RpcCommand implements Serializable {
    private int            id;
    private RpcCommandType type;
    private InvokeContext  invokeContext;
    private ProtocolType   protocolType;
    private byte           classLen;
    private byte           headerLen;
    private int            ContentLen;
    private byte[]         className;
    private byte[]         head;
    private byte[]         content;

    public abstract void serialize();

    public abstract void deserialize();
}
