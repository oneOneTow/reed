package com.think.reed.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.AbstractByteBuf;
import lombok.Data;

@Data
public class ReedRequest {

    private String serverName;

    private Map<String, Object> requestProps;

    private transient Method method;
    private transient String interfaceName;

    private transient byte serializeType;

    private transient AbstractByteBuf data;

    private transient String invokeType;

    private transient Integer timeout;

    /**
     * Gets request prop.
     *
     * @param key
     *            the key
     * @return request prop
     */
    public Object getRequestProp(String key) {
        return requestProps != null ? requestProps.get(key) : null;
    }

    /**
     * Add request prop.
     *
     * @param key
     *            the key
     * @param value
     *            the value
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
     * @param key
     *            the key
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
     * @param map
     *            the map
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