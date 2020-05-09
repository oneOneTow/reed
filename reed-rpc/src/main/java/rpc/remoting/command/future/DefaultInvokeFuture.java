package rpc.remoting.command.future;

import rpc.remoting.InvokeCallback;
import rpc.remoting.InvokeContext;
import rpc.remoting.command.CommandFactory;
import rpc.remoting.command.RpcResponseCommand;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

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

    // 未搞清楚作用
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private Timeout timeout;

    private InvokeContext invokeContext;

    private CommandFactory commandFactory;

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
}
