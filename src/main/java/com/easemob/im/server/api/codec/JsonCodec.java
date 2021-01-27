package com.easemob.im.server.api.codec;

import com.easemob.im.server.api.Codec;
import com.easemob.im.server.exception.EMJsonException;
import com.easemob.im.server.exception.EMUnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.io.IOException;

public class JsonCodec implements Codec {

    public ObjectMapper objectMapper;

    public JsonCodec() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ByteBuf encode(Object object) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            byte[] bytes = this.objectMapper.writeValueAsBytes(object);
            buffer.writeBytes(bytes);
        } catch (JsonProcessingException e) {
            System.out.println(String.format("could not encode object: %s", e.getMessage()));
            throw new EMJsonException(String.format("could not encode object: %s", e.getMessage()));
        }

        return buffer;
    }

    public <T> T decode(ByteBuf buffer, Class<T> tClass) {

        byte[] array;
        final int offset;
        int len = buffer.readableBytes();
        if (buffer.hasArray()) {
            array = buffer.array();
            offset = buffer.arrayOffset() + buffer.readerIndex();
        } else {
            array = ByteBufUtil.getBytes(buffer, buffer.readerIndex(), len, false);
            offset = 0;
        }

        try {
            return this.objectMapper.readValue(array, offset, len, tClass);
        } catch (IOException e) {
            System.out.println(String.format("could not decode class %s: %s", tClass.getName(), e.getMessage()));
            throw new EMUnknownException(String.format("could not decode class %s: %s", tClass.getName(), e.getMessage()));
        }
    }


}
