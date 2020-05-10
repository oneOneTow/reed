package connect.metaobject;

import protocol.ProtocolType;
import rpc.remoting.command.future.InvokeFuture;
import util.RemoteUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhiqing.lu
 * @version: v1.0.0
 * @date: 2020/5/2 18:42
 **/
@Data
public class Connection {

    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    public static final AttributeKey<Connection> CONNECTION = AttributeKey.valueOf("connection");

    /**
     * protocol
     */
    public static final AttributeKey<ProtocolType> protocol = AttributeKey.newInstance("protocol");

    /**
     * netty Channel
     */
    private Channel channel;

    /**
     * 没有任何引用
     */
    private static final int NO_REFERENCE = 0;

    /**
     * url
     */
    private Url url;

    /**
     * poolKeys
     */
    private Set<String> poolKeys;

    /**
     * 被应用次数
     */
    private final AtomicInteger referenceCount = new AtomicInteger();

    /**
     * 标记是否已经关闭
     */
    private AtomicBoolean closed = new AtomicBoolean(false);

    private final ConcurrentHashMap<Integer, InvokeFuture> invokeFutureMap = new ConcurrentHashMap<>(4);

    public Connection(Channel channel) {
        this.channel = channel;
        this.channel.attr(CONNECTION).set(this);
        this.channel.attr(protocol);
    }

    public Connection(Channel channel, Url url) {
        this.channel = channel;
        this.url = url;
        this.channel.attr(CONNECTION).set(this);
        this.channel.attr(protocol);
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 增加一次引用次数
     */
    public void incrReference() {
        this.referenceCount.getAndIncrement();
    }

    /**
     * 减一次引用次数
     */
    public void decrReference() {
        this.referenceCount.getAndDecrement();
    }

    /**
     * 是否没有被引用
     * 
     * @return
     */
    public boolean isNoReferencing() {
        return this.referenceCount.get() == NO_REFERENCE;
    }

    /**
     * 连接是否正常
     * 
     * @return
     */
    public boolean isFine() {
        return this.channel != null && this.channel.isActive();
    }

    public void close() {
        // cas
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        try {
            if (null == this.getChannel()) {
                return;
            }
            this.getChannel().close().addListener(future -> {
                if (logger.isInfoEnabled()) {
                    logger.info("Close the connection to remote address={}, result={}, cause={}",
                        RemoteUtils.parseRemoteAddress(Connection.this.getChannel()), future.isSuccess(),
                        future.cause());
                }
            });

        } catch (Exception e) {
            logger.warn("Exception caught when closing connection {}",
                RemoteUtils.parseRemoteAddress(Connection.this.getChannel()), e);
        }
    }

    public Set<String> getPoolKeys() {
        return new HashSet<String>(poolKeys);
    }

    public InvokeFuture removeInvokeFuture(int cmdId) {
        return this.invokeFutureMap.remove(cmdId);
    }

    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) this.channel.remoteAddress();
    }
}
