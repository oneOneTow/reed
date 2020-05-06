package com.think.reed.rpc.remoting.process;

import com.think.reed.connect.metaobject.Connection;
import com.think.reed.rpc.remoting.command.future.InvokeFuture;
import com.think.reed.rpc.remoting.RemotingContext;
import com.think.reed.rpc.remoting.command.CommandFactory;
import com.think.reed.rpc.remoting.command.RpcResponseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * response command 处理器
 * 
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 18:26
 **/
public class ResponseProcessor extends AbstractRemotingProcessor<RpcResponseCommand> {

    private static final Logger logger = LoggerFactory.getLogger(ResponseProcessor.class);

    public ResponseProcessor() {}

    public ResponseProcessor(CommandFactory commandFactory) {
        super(commandFactory);
    }

    @Override
    public void process(RemotingContext ctx, RpcResponseCommand cmd, ExecutorService defaultExecutor) {
        // 拿到server response 将response设置在InvokeFuture中
        Connection conn = ctx.getChannelContext().channel().attr(Connection.CONNECTION).get();
        InvokeFuture future = conn.removeInvokeFuture(cmd.getId());
        try {
            if (future != null) {
                future.putResponse(cmd);
                future.cancelTimeout();
                try {
                    // 不知道作甚么用，后期研究
                    // future.executeInvokeCallback();
                } catch (Exception e) {
                    logger.error("Exception caught when executing invoke callback, id={}", cmd.getId(), e);
                }
            } else {
                logger.warn("Cannot find InvokeFuture, maybe already timeout, id={}", cmd.getId());
            }
        } finally {

        }
    }

}
