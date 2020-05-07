package connect.strategy;

import connect.metaobject.Connection;

import java.util.List;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 19:21
 **/
public interface ConnectionSelectStrategy {
    /**
     * select strategy
     *
     * @param conns
     * @return
     */
    Connection select(List<Connection> conns);
}
