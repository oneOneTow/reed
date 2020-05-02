package com.think.reed.connect;

import com.think.reed.connect.metaobject.Connection;
import com.think.reed.connect.metaobject.Url;
import com.think.reed.exception.ConnectionException;

import java.util.List;

/**
 * // TODO: 实现逻辑
 * 
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 19:34
 **/
public class DefaultConnectionManager implements ConnectionManager {

    @Override
    public void add(Connection connection) {

    }

    @Override
    public void add(Connection connection, String poolKey) {

    }

    @Override
    public Connection get(String poolKey) {
        return null;
    }

    @Override
    public List<Connection> getAll(String poolKey) {
        return null;
    }

    @Override
    public void remove(Connection connection) {

    }

    @Override
    public void remove(Connection connection, String poolKey) {

    }

    @Override
    public void removeAndCloseAll(String poolKey) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public void checkConnAvaliable(Connection connection) throws ConnectionException {

    }

    @Override
    public Connection getAndCreateIfAbsent(Url url) {
        return null;
    }

    @Override
    public Connection create(Url url) {
        return null;
    }

    @Override
    public Connection create(String address, int connectTimeout) {
        return null;
    }

    @Override
    public Connection create(String ip, int port, int connectTimeout) {
        return null;
    }
}
