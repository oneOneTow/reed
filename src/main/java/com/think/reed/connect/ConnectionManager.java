package com.think.reed.connect;

import com.think.reed.connect.metaobject.Connection;
import com.think.reed.connect.metaobject.ConnectionPool;
import com.think.reed.connect.metaobject.Url;
import com.think.reed.connect.strategy.ConnectionSelectStrategy;
import com.think.reed.exception.ConnectionException;

import java.util.List;

/**
 * @author zhiqing.lu
 * @date 2020/5/2 18:49
 * @version v1.0.0
 **/
public interface ConnectionManager {

    /**
     * Add a connection to {@link ConnectionPool}.
     * 
     * @param connection
     * @author zhiqing.lu
     */
    void add(Connection connection);

    /**
     * Add a connection to {@link ConnectionPool} with the specified poolKey.
     *
     * @param connection
     *            an available connection, you should {@link #checkConnIsAvaliable(Connection)} this connection before
     *            add
     * @param poolKey
     *            unique key of a {@link ConnectionPool}
     * @author zhiqing.lu
     */
    void add(Connection connection, String poolKey);

    /**
     * Get a connection from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey
     *            unique key of a {@link ConnectionPool}
     * @return a {@link Connection} selected by {@link ConnectionSelectStrategy}<br>
     *         or return {@code null} if there is no {@link ConnectionPool} mapping with poolKey<br>
     *         or return {@code null} if there is no {@link Connection} in {@link ConnectionPool}.
     * @author zhiqing.lu
     */
    Connection get(String poolKey);

    /**
     * Get all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey
     *            unique key of a {@link ConnectionPool}
     * @return a list of {@link Connection}<br>
     *         or return an empty list if there is no {@link ConnectionPool} mapping with poolKey.
     * @author zhiqing.lu
     */
    List<Connection> getAll(String poolKey);

    /**
     * Remove a {@link Connection} from all {@link ConnectionPool} with the poolKeys in {@link Connection}, and close
     * it.
     * 
     * @author zhiqing.lu
     */
    void remove(Connection connection);

    /**
     * Remove and close a {@link Connection} from {@link ConnectionPool} with the specified poolKey.
     *
     * @param connection
     *            target connection
     * @param poolKey
     *            unique key of a {@link ConnectionPool}
     * @author zhiqing.lu
     */
    void remove(Connection connection, String poolKey);

    /**
     * Remove and close all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey
     *            unique key of a {@link ConnectionPool}
     * @author zhiqing.lu
     */
    void removeAndCloseAll(String poolKey);

    /**
     * Remove and close all connections from all {@link ConnectionPool}.
     * 
     * @author zhiqing.lu
     */
    void removeAll();

    /**
     * check a connection whether available, if not, throw ConnectionException
     * 
     * @param connection
     * @return
     * @author zhiqing.lu
     */
    void checkConnAvaliable(Connection connection) throws ConnectionException;

    /**
     * Get a connection using {@link Url}, if {@code null} then create and add into {@link ConnectionPool}. The
     * connection number of {@link ConnectionPool} is decided by {@link Url#getConnNum()}
     * 
     * @param url
     * @return
     * @author zhiqing.lu
     */
    Connection getAndCreateIfAbsent(Url url);

    /**
     * Create a connection using specified {@link Url}.
     *
     * @param url
     *            {@link Url} contains connect infos.
     * @author zhiqing.lu
     */
    Connection create(Url url);

    /**
     * Create a connection using specified {@link String} address.
     *
     * @param address
     *            a {@link String} address, e.g. 127.0.0.1:1111
     * @param connectTimeout
     *            an int connect timeout value
     * @return the created {@link Connection}
     * @author zhiqing.lu
     */
    Connection create(String address, int connectTimeout);

    /**
     * Create a connection using specified ip and port.
     *
     * @param ip
     *            connect ip, e.g. 127.0.0.1
     * @param port
     *            connect port, e.g. 1111
     * @return the created {@link Connection}
     * @author zhiqing.lu
     */
    Connection create(String ip, int port, int connectTimeout);
}
