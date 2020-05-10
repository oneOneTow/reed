package rpc.remoting.command.future;

import exception.ReedException;
import rpc.remoting.InvokeCallback;
import rpc.remoting.InvokeContext;
import rpc.remoting.command.CommandFactory;
import rpc.remoting.command.RpcCommand;
import rpc.remoting.command.RpcResponseCommand;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 12:14
 **/
public class DefaultInvokeFuture implements InvokeFuture {
    private static final Logger logger = LoggerFactory.getLogger(DefaultInvokeFuture.class);

    private int invokeId;

    private InvokeCallback callback;

    private volatile RpcResponseCommand responseCommand;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private Timeout timeout;

    private InvokeContext invokeContext;

    private CommandFactory commandFactory;

    public DefaultInvokeFuture(int invokeId, Timeout timeout, InvokeContext invokeContext, CommandFactory commandFactory) {
        this.invokeId = invokeId;
        this.timeout = timeout;
        this.invokeContext = invokeContext;
        this.commandFactory = commandFactory;
    }

    @Override
    public void putResponse(RpcResponseCommand response) {
        this.responseCommand = response;
        this.countDownLatch.countDown();
    }

    @Override
    public void cancelTimeout() {
        if (this.timeout != null) {
            this.timeout.cancel();
        }
    }

    @Override
    public RpcCommand waitResponse(int timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }
}
