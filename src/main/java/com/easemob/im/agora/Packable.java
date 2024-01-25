package com.easemob.im.agora;

public interface Packable {
    ByteBuf marshal(ByteBuf out);
}
