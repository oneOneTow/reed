package com.think.reed.rpc.remoting.process;

import com.think.reed.rpc.remoting.RemotingContext;
import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:05
 **/
public interface RemotingProcessor<T> {

    void process(RemotingContext ctx, T cmd, ExecutorService defaultExecutor);

    ExecutorService getExecutor();

    void setExecutor(ExecutorService executor);
}
