package rpc.remoting.command;

import exception.ReedException;
import lombok.Data;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 13:24
 **/
@Data
public class RpcResponseCommand extends RpcCommand {
    protected Object responseObject;
    protected ResponseStatus responseStatus;
    protected long responseTimeMillis;
    protected InetSocketAddress responseHost;
    protected Throwable cause;

    public RpcResponseCommand() {
    }

    public RpcResponseCommand(int id, Object responseObject) {
        this.id = id;
        this.responseObject = responseObject;
    }

    @Override
    public void serialize() {

    }

    @Override
    public void deserialize() {

    }

    public enum ResponseStatus {
        /**
         * serial exception
         */
        SERVER_SERIAL_EXCEPTION((byte)0),
        /**
         * thread busi
         */
        SERVER_THREADPOOL_BUSY((byte)1),
        /**
         * success
         */
        SUCCESS((byte)2),
        /**
         * CLIENT_SEND_ERROR
         */
        CLIENT_SEND_ERROR((byte)3),
        /**
         * TIMEOUT
         */
        TIMEOUT((byte)4);

        private byte code;

        ResponseStatus(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }

        public void setCode(byte code) {
            this.code = code;
        }

        public static ResponseStatus valueOfCode(byte code){
            for (ResponseStatus status: ResponseStatus.values()){
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new ReedException("error response code");
        }
    }
}
