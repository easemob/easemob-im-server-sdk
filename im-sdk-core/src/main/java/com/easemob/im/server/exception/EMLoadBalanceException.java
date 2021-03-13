package com.easemob.im.server.exception;

import com.easemob.im.server.EMException;

public class EMLoadBalanceException extends EMException {
    public EMLoadBalanceException(String message) {
        super(message);
    }
}
