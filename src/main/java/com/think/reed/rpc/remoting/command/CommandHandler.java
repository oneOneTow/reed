package com.think.reed.rpc.remoting.command;

import com.think.reed.rpc.remoting.RemotingContext;
import com.think.reed.rpc.remoting.process.RemotingProcessor;

import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:03
 **/

public interface CommandHandler {
    /**
     * Handle the command.
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    void handleCommand(RemotingContext ctx, Object msg) throws Exception;

    /**
     * Register processor for command with specified code.
     *
     * @param cmd
     * @param processor
     */
    void registerProcessor(RpcCommandType cmd, RemotingProcessor<?> processor);

    /**
     * Register default executor for the handler.
     *
     * @param executor
     */
    void registerDefaultExecutor(ExecutorService executor);

    /**
     * Get default executor for the handler.
     */
    ExecutorService getDefaultExecutor();
}
