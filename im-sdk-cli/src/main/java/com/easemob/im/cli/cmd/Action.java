package com.easemob.im.cli.cmd;

import org.springframework.util.StringUtils;

public interface Action {

    default boolean hasText(String string) {
        return StringUtils.hasText(string);
    }
}
