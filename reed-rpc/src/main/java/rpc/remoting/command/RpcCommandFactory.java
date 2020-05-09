package rpc.remoting.command;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 00:22
 **/
public class RpcCommandFactory implements CommandFactory {

    @Override
    public <T extends RpcCommand> T createRequestCommand(Object requestObject) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createResponse(Object responseObject, RpcCommand requestCmd) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createExceptionResponse(int id, String errMsg) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createExceptionResponse(int id, Throwable t, String errMsg) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status,
                                                            Throwable t) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createTimeoutResponse(InetSocketAddress address) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createSendFailedResponse(InetSocketAddress address, Throwable throwable) {
        return null;
    }

    @Override
    public <T extends RpcCommand> T createConnectionClosedResponse(InetSocketAddress address, String message) {
        return null;
    }
}
