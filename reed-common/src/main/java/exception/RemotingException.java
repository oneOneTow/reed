package exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 01:29
 **/
public class RemotingException extends NestableRuntimeException {
    public RemotingException(String errorCode){
        super(errorCode);
    }

    public RemotingException(String errorCode, Throwable cause){
        super(errorCode, cause);
    }

    public RemotingException(String errorCode, String errorDesc){
        super(errorCode + ":" + errorDesc);
    }

    public RemotingException(String errorCode, String errorDesc, Throwable cause){
        super(errorCode + ":" + errorDesc, cause);
    }

    public RemotingException(Throwable cause){
        super(cause);
    }
}
