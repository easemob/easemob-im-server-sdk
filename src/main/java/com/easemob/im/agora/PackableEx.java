package com.easemob.im.agora;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
