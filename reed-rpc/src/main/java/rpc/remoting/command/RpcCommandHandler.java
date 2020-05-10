package rpc.remoting.command;

import rpc.remoting.*;
import rpc.remoting.process.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:12
 **/
@ChannelHandler.Sharable
public class RpcCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcCommandHandler.class);

    protected ProcessorManager processorManager;

    protected CommandFactory commandFactory;

    public RpcCommandHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        this.processorManager = new ProcessorManager();
        // process request
        this.processorManager.registerProcessor(RpcCommandType.REQUEST, new RequestProcessor(this.commandFactory));
        // process response
        this.processorManager.registerProcessor(RpcCommandType.RESPONSE, new ResponseProcessor());

        this.processorManager.registerDefaultProcessor(new AbstractRemotingProcessor<RpcCommand>() {
            @Override
            public void process(RemotingContext ctx, RpcCommand cmd, ExecutorService defaultExecutor) {
                logger.error("No processor available for command code {}, msgId {}", cmd.getType(), cmd.getId());
            }
        });
    }

    @Override
    public void handleCommand(RemotingContext ctx, Object msg) throws Exception {
        try {
            final RpcCommand cmd = (RpcCommand)msg;
            final RemotingProcessor processor = processorManager.getProcessor(cmd.getType());
            processor.process(ctx, cmd, processorManager.getDefaultExecutor());
        } catch (final Throwable t) {
            processException(ctx, msg, t);
        }
    }

    @Override
    public void registerProcessor(RpcCommandType cmd, RemotingProcessor<?> processor) {
        this.processorManager.registerProcessor(cmd, processor);
    }

    @Override
    public void registerDefaultExecutor(ExecutorService executor) {
        this.processorManager.registerDefaultExecutor(executor);
    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return this.processorManager.getDefaultExecutor();
    }

    private void processException(RemotingContext ctx, Object msg, Throwable t) {
        processExceptionForSingleCommand(ctx, msg, t);
    }

    private void processExceptionForSingleCommand(RemotingContext ctx, Object msg, Throwable t) {
        final int id = ((RpcCommand)msg).getId();
        final String emsg =
            "Exception caught when processing " + ((msg instanceof RpcRequestCommand) ? "request, id=" : "response, id=");
        logger.warn(emsg + id, t);
        if (msg instanceof RpcRequestCommand) {
            if (t instanceof RejectedExecutionException) {
                final RpcResponseCommand response = this.commandFactory.createExceptionResponse(id,
                                                                                                RpcResponseCommand.ResponseStatus.SERVER_THREADPOOL_BUSY);

                ctx.getChannelContext().writeAndFlush(response).addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            if (logger.isInfoEnabled()) {
                                logger.info("Write back exception response done, requestId={}, status={}", id,
                                    response.getResponseStatus());
                            }
                        } else {
                            logger.error("Write back exception response failed, requestId={}", id, future.cause());
                        }
                    }

                });
            }
        }
    }

}
