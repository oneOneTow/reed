package aop.aspect;

import java.util.concurrent.ConcurrentHashMap;

public class ReedServerCache {
    // first not ca
    public static ConcurrentHashMap<String, Object> cacheReedServer = null;

    public static Object getCacheReedServer(String instance) {
        if (null == cacheReedServer) {
            cacheReedServer = new ConcurrentHashMap<String, Object>(12);
        }
        return cacheReedServer.get(instance);
    }

    public static void putIfAbsent(String instance, Object o) {
        if (null == cacheReedServer) {
            cacheReedServer = new ConcurrentHashMap<String, Object>(12);
        }
        cacheReedServer.putIfAbsent(instance, o);
    }

    public static boolean reedServerIfExists(String instance) {
        return null != cacheReedServer.get(instance);
    }
}
