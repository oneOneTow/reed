package rpc.remoting.command;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 00:22
 **/
public class RpcCommandFactory implements CommandFactory {

    private static RpcCommandFactory commandFactory;
    private RpcCommandFactory(){}

    public static RpcCommandFactory getInstance(){
        if (null == commandFactory) {
            synchronized (RpcCommandFactory.class){
                if (null == commandFactory) {
                    commandFactory = new RpcCommandFactory();
                }
            }
        }
        return commandFactory;
    }

    @Override
    public RpcRequestCommand createRequestCommand(Object requestObject) {
        return new RpcRequestCommand(requestObject);
    }

    @Override
    public RpcResponseCommand createResponse(Object responseObject, RpcCommand requestCmd) {
        RpcResponseCommand response = new RpcResponseCommand(requestCmd.getId(), responseObject);
        response.setSerializer(requestCmd.getSerializer());
        response.setResponseStatus(RpcResponseCommand.ResponseStatus.SUCCESS);
        return response;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(int id, String errMsg) {
        return null;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(int id, Throwable t, String errMsg) {
        return null;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status) {
        return null;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status,
                                                            Throwable t) {
        return null;
    }

    @Override
    public RpcResponseCommand createTimeoutResponse(InetSocketAddress address) {
        RpcResponseCommand responseCommand = new RpcResponseCommand();
        responseCommand.setResponseStatus(RpcResponseCommand.ResponseStatus.TIMEOUT);
        responseCommand.setResponseTimeMillis(System.currentTimeMillis());
        responseCommand.setResponseHost(address);
        return responseCommand;
    }

    @Override
    public RpcResponseCommand createSendFailedResponse(InetSocketAddress address, Throwable throwable) {
        RpcResponseCommand responseCommand = new RpcResponseCommand();
        responseCommand.setResponseStatus(RpcResponseCommand.ResponseStatus.CLIENT_SEND_ERROR);
        responseCommand.setResponseTimeMillis(System.currentTimeMillis());
        responseCommand.setResponseHost(address);
        responseCommand.setCause(throwable);
        return responseCommand;
    }

    @Override
    public RpcResponseCommand createConnectionClosedResponse(InetSocketAddress address, String message) {
        return null;
    }
}
