package com.easemob.im.server.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EMKeyValue {

    private final String key;

    private final Type type;

    private final Object value;

    private EMKeyValue(String key, Type type, Object value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public static EMKeyValue of(String key, boolean value) {
        return new EMKeyValue(key, Type.BOOL, value);
    }

    public static EMKeyValue of(String key, int value) {
        return new EMKeyValue(key, Type.INT, value);
    }

    public static EMKeyValue of(String key, long value) {
        return new EMKeyValue(key, Type.LLINT, value);
    }

    public static EMKeyValue of(String key, float value) {
        return new EMKeyValue(key, Type.FLOAT, value);
    }

    public static EMKeyValue of(String key, double value) {
        return new EMKeyValue(key, Type.DOUBLE, value);
    }

    public static EMKeyValue of(String key, String value) {
        return new EMKeyValue(key, Type.STRING, value);
    }

    public static EMKeyValue of(String k, Object v) {
        if (v instanceof Boolean) {
            return EMKeyValue.of(k, (boolean) v);
        } else if (v instanceof Integer) {
            return EMKeyValue.of(k, (int) v);
        } else if (v instanceof Long) {
            return EMKeyValue.of(k, (long) v);
        } else if (v instanceof Float) {
            return EMKeyValue.of(k, (float) v);
        } else if (v instanceof Double) {
            return EMKeyValue.of(k, (double) v);
        } else if (v instanceof String) {
            return EMKeyValue.of(k, (String) v);
        } else {
            return new EMKeyValue(k, Type.OBJECT, v);
        }
    }

    public static Set<EMKeyValue> of(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return new LinkedHashSet<>();
        }
        Set<EMKeyValue> emKeyValues = new LinkedHashSet<>(map.size());
        map.forEach((k, v) -> emKeyValues.add(EMKeyValue.of(k, v)));
        return emKeyValues;
    }

    public String key() {
        return this.key;
    }

    public Type type() {
        return this.type;
    }

    @SuppressWarnings("unchecked")
    public boolean asBoolean() {
        return (boolean) this.value;
    }

    @SuppressWarnings("unchecked")
    public int asInt() {
        return (int) this.value;
    }

    @SuppressWarnings("unchecked")
    public long asLong() {
        return (long) this.value;
    }

    @SuppressWarnings("unchecked")
    public long asFloat() {
        return (long) this.value;
    }

    @SuppressWarnings("unchecked")
    public double asDouble() {
        return (double) this.value;
    }

    @SuppressWarnings("unchecked")
    public String asString() {
        return (String) this.value;
    }

    public Object asObject() {
        return this.value;
    }

    public enum Type {
        BOOL,
        INT,
        UINT,
        LLINT,
        FLOAT,
        DOUBLE,
        STRING,
        JSON_STRING,
        OBJECT,
    }
}
