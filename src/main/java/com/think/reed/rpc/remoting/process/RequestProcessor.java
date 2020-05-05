package com.think.reed.rpc.remoting.process;

import com.think.reed.rpc.remoting.RemotingContext;
import com.think.reed.rpc.remoting.command.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.lang.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 18:25
 **/
public class RequestProcessor extends AbstractRemotingProcessor<RpcRequestCommand> {
    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    public RequestProcessor(CommandFactory commandFactory) {
        super(commandFactory);
    }

    @Override
    public void process(RemotingContext ctx, RpcRequestCommand cmd, ExecutorService defaultExecutor) {
        if (!deserializeRequestCommand(ctx, cmd, 0)) {
            return;
        }
        // TODO: 处理拿到request Command 做服务端真实方法调用

    }

    private boolean deserializeRequestCommand(RemotingContext ctx, RpcRequestCommand cmd, int level) {
        // TODO:
        return true;
    }


    public void sendResponseIfNecessary(final RemotingContext ctx, byte type, final RpcCommand response) {
        final int id = response.getId();

        RpcCommand serializedResponse = response;
        try {
            response.serialize();
        } catch (SerializationException e) {
            String errMsg =
                "SerializationException occurred when sendResponseIfNecessary in RpcRequestProcessor, id=" + id;
            logger.error(errMsg, e);
            serializedResponse = this.getCommandFactory().createExceptionResponse(id,
                                                                                  RpcResponseCommand.ResponseStatus.SERVER_SERIAL_EXCEPTION, e);
            try {
                serializedResponse.serialize();// serialize again for exception response
            } catch (SerializationException e1) {
                // should not happen
                logger.error("serialize SerializationException response failed!");
            }
        } catch (Throwable t) {
            String errMsg =
                "Serialize RpcResponseCommand failed when sendResponseIfNecessary in RpcRequestProcessor, id=" + id;
            logger.error(errMsg, t);
            serializedResponse = this.getCommandFactory().createExceptionResponse(id, t, errMsg);
        }

        ctx.writeAndFlush(serializedResponse).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rpc response sent! requestId=" + id);
                }
                if (!future.isSuccess()) {
                    logger.error("Rpc response send failed! id=" + id);
                }
            }
        });

    }

}
