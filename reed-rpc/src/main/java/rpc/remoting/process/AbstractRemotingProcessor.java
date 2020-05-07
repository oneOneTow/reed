package rpc.remoting.process;

import rpc.remoting.RemotingContext;
import rpc.remoting.command.CommandFactory;
import rpc.remoting.command.RpcCommand;

import java.util.concurrent.ExecutorService;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 18:27
 **/
public abstract class AbstractRemotingProcessor<T extends RpcCommand> implements RemotingProcessor<T> {

    /**
     * 异步任务执行器
     */
    private ExecutorService executor;

    private CommandFactory commandFactory;

    public AbstractRemotingProcessor() {
    }

    public AbstractRemotingProcessor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public AbstractRemotingProcessor(ExecutorService executor, CommandFactory commandFactory) {
        this.executor = executor;
        this.commandFactory = commandFactory;
    }

    @Override
    public abstract void process(RemotingContext ctx, T cmd, ExecutorService defaultExecutor);

    @Override
    public ExecutorService getExecutor() {
        return this.executor;
    }

    @Override
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
}
