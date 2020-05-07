package connect;

import connect.factory.ConnectionFactory;
import connect.metaobject.Connection;
import connect.metaobject.ConnectionPool;
import connect.metaobject.Url;
import connect.strategy.ConnectionSelectStrategy;
import exception.ConnectionException;
import exception.RemotingException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * // TODO: 实现逻辑
 * 
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 19:34
 **/
public class DefaultConnectionManager implements ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectionManager.class);

    private ConcurrentHashMap<String, ConnectionPool> connectionPoolMap = new ConcurrentHashMap<>();

    protected ConnectionSelectStrategy connectionSelectStrategy;
    protected ConnectionFactory connectionFactory;

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy) {
        this.connectionSelectStrategy = connectionSelectStrategy;
    }

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy,
        ConnectionFactory connectionFactory) {
        this.connectionSelectStrategy = connectionSelectStrategy;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Connection connection) {
        Set<String> poolKeys = connection.getPoolKeys();
        for (String poolKey : poolKeys) {
            this.add(connection, poolKey);
        }
    }

    @Override
    public void add(Connection connection, String poolKey) {
        ConnectionPool pool = null;
        try {
            // get or create an empty connection pool
            pool = connectionPoolMap.get(poolKey);
        } catch (Exception e) {
            // should not reach here.
            logger.error("[NOTIFYME] Exception occurred when getOrCreateIfAbsent an empty ConnectionPool!", e);
        }
        if (pool != null) {
            pool.add(connection);
        } else {
            // should not reach here.
            logger.error("[NOTIFYME] Connection pool NULL!");
        }
    }

    @Override
    public Connection get(String poolKey) {
        ConnectionPool pool = connectionPoolMap.get(poolKey);
        return null == pool ? null : pool.get();
    }

    @Override
    public List<Connection> getAll(String poolKey) {
        ConnectionPool pool = connectionPoolMap.get(poolKey);
        return null == pool ? null : pool.getAll();
    }

    @Override
    public void remove(Connection connection) {
        if (null == connection) {
            return;
        }
        Set<String> poolKeys = connection.getPoolKeys();
        if (null == poolKeys || poolKeys.isEmpty()) {
            connection.close();
            logger.warn("Remove and close a standalone connection.");
        } else {
            for (String poolKey : poolKeys) {
                this.remove(connection, poolKey);
            }
        }
    }

    @Override
    public void remove(Connection connection, String poolKey) {
        if (null == connection || StringUtils.isBlank(poolKey)) {
            return;
        }
        ConnectionPool pool = connectionPoolMap.get(poolKey);
        if (null == pool) {
            connection.close();
            logger.warn("Remove and close a standalone connection.");
        } else {
            pool.removeAndTryClose(connection);
        }
    }

    @Override
    public void removeAndCloseAll(String poolKey) {
        ConnectionPool pool = connectionPoolMap.get(poolKey);
        if (null != pool) {
            pool.removeAllAndTryClose();
        }
    }

    @Override
    public void checkConnAvailable(Connection connection) throws ConnectionException {
        if (connection == null) {
            throw new RemotingException("Connection is null when do check!");
        }
        if (connection.getChannel() == null || !connection.getChannel().isActive()) {
            this.remove(connection);
            throw new RemotingException("Check connection failed for address: " + connection.getUrl());
        }
        if (!connection.getChannel().isWritable()) {
            // No remove. Most of the time it is unwritable temporarily.
            throw new RemotingException(
                "Check connection failed for address: " + connection.getUrl() + ", maybe write overflow!");
        }
    }

    @Override
    public Connection getAndCreateIfAbsent(Url url) {
        ConnectionPool pool = this.getConnectionPoolAndCreateIfAbsent(url);
        // 不可能为空
        return pool.get();
    }

    private synchronized ConnectionPool getConnectionPoolAndCreateIfAbsent(Url url) {

        ConnectionPool pool = connectionPoolMap.get(url.getUniqueKey());
        if (null != pool && pool.size() > (url.getConnNum() - 1)) {
            return pool;
        }
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection(url);
        } catch (Exception e) {
            logger.info("create connection failed address is:{}", url.getOriginUrl());
            throw new RemotingException(e);
        }
        if (null == pool) {
            pool = new ConnectionPool(this.connectionSelectStrategy);
        }
        pool.add(connection);
        return pool;

    }

    @Override
    public Connection create(Url url) {
        Connection conn = null;
        try {
            conn = this.connectionFactory.createConnection(url);
        } catch (Exception e) {
            throw new RemotingException("Create connection failed. The address is " + url.getOriginUrl(), e);
        }
        return conn;
    }

    @Override
    public Connection create(String ip, int port, int connectTimeout) {
        Connection conn = null;
        try {
            conn = this.connectionFactory.createConnection(ip, port, connectTimeout);
        } catch (Exception e) {
            throw new RemotingException("Create connection failed. The address is " + ip + ":" + port, e);
        }
        return conn;
    }

    @Override
    public void removeAll() {
        connectionPoolMap.forEach((k,v)->{
            removeAndCloseAll(k);
        });
    }
}
