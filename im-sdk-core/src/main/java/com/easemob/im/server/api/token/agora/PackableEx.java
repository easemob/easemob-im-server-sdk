package com.easemob.im.server.api.token.agora;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
