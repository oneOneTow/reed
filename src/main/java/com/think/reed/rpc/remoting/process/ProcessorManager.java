package com.think.reed.rpc.remoting.process;

import com.think.reed.rpc.remoting.command.RpcCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:17
 **/
public class ProcessorManager {
    private static final Logger                                     logger         = LoggerFactory.getLogger(ProcessorManager.class);
    /**
     * 处理器容器
     */
    private ConcurrentHashMap<RpcCommandType, RemotingProcessor<?>> cmd2processors = new ConcurrentHashMap<>(4);
    /**
     * 默认处理器
     */
    private RemotingProcessor<?>                                    defaultProcessor;

    private ExecutorService defaultExecutor;

    public RemotingProcessor getProcessor(RpcCommandType cmdCode) {
        RemotingProcessor<?> processor = this.cmd2processors.get(cmdCode);
        if (processor != null) {
            return processor;
        }
        return this.defaultProcessor;
    }

    public ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    public void registerDefaultProcessor(RemotingProcessor<?> processor) {
        if (this.defaultProcessor == null) {
            this.defaultProcessor = processor;
        } else {
            throw new IllegalStateException(
                "The defaultProcessor has already been registered: " + this.defaultProcessor.getClass());
        }
    }

    public void registerProcessor(RpcCommandType cmdCode, RemotingProcessor<?> processor) {
        if (this.cmd2processors.containsKey(cmdCode)) {
            logger.warn("Processor for cmd={} is already registered, the processor is {}, and changed to {}", cmdCode,
                cmd2processors.get(cmdCode).getClass().getName(), processor.getClass().getName());
        }
        this.cmd2processors.put(cmdCode, processor);
    }

    public void registerDefaultExecutor(ExecutorService executor) {
        this.defaultExecutor = executor;
    }
}
