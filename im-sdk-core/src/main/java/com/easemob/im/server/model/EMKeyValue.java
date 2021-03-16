package com.easemob.im.server.model;

public class EMKeyValue {

    private final String key;

    private final Type type;

    private final Object value;

    public enum Type {
        BOOL,
        INT,
        UINT,
        LLINT,
        FLOAT,
        DOUBLE,
        STRING,
        JSON_STRING,
    }

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
}
