package com.think.reed.rpc.remoting.server;

import com.think.reed.rpc.remoting.command.RpcCommandType;
import com.think.reed.rpc.remoting.process.RemotingProcessor;

import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 21:56
 **/
public interface RemotingServer {
    /**
     * init the server
     */
    void init();

    /**
     * Start the server.
     */
    boolean start();

    /**
     * Stop the server.
     *
     * Remoting server can not be used any more after stop. If you need, you should destroy it, and instantiate another
     * one.
     */
    boolean stop();

    /**
     * Get the ip of the server.
     *
     * @return ip
     */
    String ip();

    /**
     * Get the port of the server.
     *
     * @return listened port
     */
    int port();

    /**
     * Register processor for command with the command code.
     *
     * @param protocolCode
     *            protocol code
     * @param commandCode
     *            command code
     * @param processor
     *            processor
     */
    void registerProcessor(byte protocolCode, RpcCommandType commandCode, RemotingProcessor<?> processor);

    /**
     * Register default executor service for server.
     *
     * @param protocolCode
     *            protocol code
     * @param executor
     *            the executor service for the protocol code
     */
    void registerDefaultExecutor(byte protocolCode, ExecutorService executor);
}
