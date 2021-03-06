package connect.factory;

import connect.metaobject.Connection;
import connect.metaobject.Url;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 01:06
 **/
public interface ConnectionFactory {

    /**
     * 创建连接
     * 
     * @param url
     * @return
     * @throws Exception
     */
    Connection createConnection(Url url) throws Exception;

    Connection createConnection(String targetIP, int targetPort, int connectTimeout) throws Exception;
}
