package com.easemob.im.server.api;

import io.netty.buffer.ByteBuf;

public interface Codec {
    ByteBuf encode(Object object);

    <T> T decode(ByteBuf buffer, Class<T> tClass);
}
