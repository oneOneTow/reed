package connect.strategy;

import connect.metaobject.Connection;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 22:05
 **/
public class RandomConnectionSelectStrategy implements ConnectionSelectStrategy {

    @Override
    public Connection select(List<Connection> conns) {
        if (null == conns || conns.isEmpty()) {
            return null;
        }
        int connsSize = conns.size();
        return conns.get(RandomUtils.nextInt(connsSize));
    }
}
