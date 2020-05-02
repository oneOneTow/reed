package com.think.reed.connect.metaobject;

import com.think.reed.connect.strategy.ConnectionSelectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author zhiqing.lu
 * @date 2020/5/2 18:43
 * @version v1.0.0
 **/
public class ConnectionPool {
    private static final Logger                           logger = LoggerFactory.getLogger(ConnectionPool.class);
    /**
     * 连接选举策略器
     */
    private              ConnectionSelectStrategy         strategy;
    /**
     * connections
     */
    private              CopyOnWriteArrayList<Connection> connections = new CopyOnWriteArrayList<Connection>();
    /**
     * 最后访问connectionPool的时间戳
     */
    private volatile     long                             lastAccessTimestamp;

    public ConnectionPool(ConnectionSelectStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 在连接池中增加一个连接
     * 
     * @param connection
     */
    public void add(Connection connection) {
        markAccess();
        if (null == connection) {
            return;
        }
        boolean res = this.connections.addIfAbsent(connection);
        if (res) {
            connection.incrReference();
        }
    }

    /**
     * 获取一个连接
     * 
     * @return
     */
    public Connection get() {
        markAccess();
        if (null != this.connections) {
            List<Connection> snapshot = new ArrayList<Connection>(this.connections);
            if (snapshot.size() > 0) {
                return this.strategy.select(snapshot);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * removeAndTryClose a connection
     *
     * @param connection
     */
    public void removeAndTryClose(Connection connection) {
        if (null == connection) {
            return;
        }
        boolean res = this.connections.remove(connection);
        if (res) {
            connection.decrReference();
        }
        if (connection.isNoReferencing()) {
            connection.close();
        }
    }

    /**
     * remove all connections
     */
    public void removeAllAndTryClose() {
        for (Connection conn : this.connections) {
            removeAndTryClose(conn);
        }
        this.connections.clear();
    }

    public boolean contains(Connection connection) {
        return this.connections.contains(connection);
    }

    public int size() {
        return this.connections.size();
    }

    public boolean isEmpty() {
        return this.connections.isEmpty();
    }

    public long getLastAccessTimestamp() {
        return this.lastAccessTimestamp;
    }

    private void markAccess() {
        this.lastAccessTimestamp = System.currentTimeMillis();
    }
}
