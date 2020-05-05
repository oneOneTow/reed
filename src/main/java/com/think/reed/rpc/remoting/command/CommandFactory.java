package com.think.reed.rpc.remoting.command;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 18:20
 **/
public interface CommandFactory {
    /**
     * create a request command with request object
     *
     * @param requestObject
     *            the request object included in request command
     * @param <T>
     * @return
     */
    <T extends RpcCommand> T createRequestCommand(final Object requestObject);


    /**
     * create a normal response with response object
     * 
     * @param responseObject
     * @param requestCmd
     * @param <T>
     * @return
     */
    <T extends RpcCommand> T createResponse(final Object responseObject, RpcCommand requestCmd);

    <T extends RpcCommand> T createExceptionResponse(int id, String errMsg);

    <T extends RpcCommand> T createExceptionResponse(int id, final Throwable t, String errMsg);

    <T extends RpcCommand> T createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status);

    <T extends RpcCommand> T createExceptionResponse(int id, RpcResponseCommand.ResponseStatus status, final Throwable t);

    <T extends RpcCommand> T createTimeoutResponse(final InetSocketAddress address);

    <T extends RpcCommand> T createSendFailedResponse(final InetSocketAddress address, Throwable throwable);

    <T extends RpcCommand> T createConnectionClosedResponse(final InetSocketAddress address, String message);
}
