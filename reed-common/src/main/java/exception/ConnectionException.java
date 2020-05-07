package exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 19:30
 **/
public class ConnectionException extends NestableRuntimeException {
    public ConnectionException(String errorCode){
        super(errorCode);
    }

    public ConnectionException(String errorCode, Throwable cause){
        super(errorCode, cause);
    }

    public ConnectionException(String errorCode, String errorDesc){
        super(errorCode + ":" + errorDesc);
    }

    public ConnectionException(String errorCode, String errorDesc, Throwable cause){
        super(errorCode + ":" + errorDesc, cause);
    }

    public ConnectionException(Throwable cause){
        super(cause);
    }
}
