package com.think.reed.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.AbstractByteBuf;
import lombok.Data;

@Data
public class ReedRequest {
    /**
     * Target app name. If progress of 'AppA' want to call the progress which contains two apps('AppB1' and 'AppB2'),
     * You need specified the target app name here. such as 'AppB2'
     */
    private String serverName;

    /**
     * Extensional properties of request
     */
    private Map<String, Object> requestProps;
    /**
     * 方法对象(为了减少反射缓存）
     */
    private transient Method method;
    /**
     * 接口名
     */
    private transient String interfaceName;
    /**
     * 序列化类型
     */
    private transient byte serializeType;
    /**
     * 请求数据
     */
    private transient AbstractByteBuf data;
    /**
     * 调用类型（客户端使用）
     */
    private transient String invokeType;
    /**
     * 用户层请求超时，调用级别（客户端使用）
     */
    private transient Integer timeout;

    /**
     * Gets request prop.
     *
     * @param key the key
     * @return request prop
     */
    public Object getRequestProp(String key) {
        return requestProps != null ? requestProps.get(key) : null;
    }

    // ====================== 下面是非传递属性 ===============

    /**
     * Add request prop.
     *
     * @param key   the key
     * @param value the value
     */
    public void addRequestProp(String key, Object value) {
        if (key == null || value == null) {
            return;
        }
        if (requestProps == null) {
            requestProps = new HashMap<String, Object>(16);
        }
        requestProps.put(key, value);
    }

    /**
     * Remove request prop.
     *
     * @param key the key
     */
    public void removeRequestProp(String key) {
        if (key == null) {
            return;
        }
        if (requestProps != null) {
            requestProps.remove(key);
        }
    }

    /**
     * Add request props.
     *
     * @param map the map
     */
    public void addRequestProps(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        if (requestProps == null) {
            requestProps = new HashMap<String, Object>(16);
        }
        requestProps.putAll(map);
    }
}



