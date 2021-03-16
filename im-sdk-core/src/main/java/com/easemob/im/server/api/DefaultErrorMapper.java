package com.easemob.im.server.api;

import com.easemob.im.server.EMException;
import com.easemob.im.server.exception.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultErrorMapper implements ErrorMapper {

    private static final Logger log = LoggerFactory.getLogger(DefaultErrorMapper.class);

    public Mono<HttpClientResponse> apply(HttpClientResponse response) {
        if (response.status().code() < 400) {
            return Mono.just(response);
        }
        return Mono.error(toException(response));
    }

    private EMException toException(HttpClientResponse response) {
        String reason = String.format("%s %s -> %d %s", response.method().toString(), response.uri(), response.status().code(), response.status().reasonPhrase());
        HttpResponseStatus status = response.status();
        if (HttpResponseStatus.BAD_REQUEST.equals(status)
                || HttpResponseStatus.METHOD_NOT_ALLOWED.equals(status)
                || HttpResponseStatus.NOT_ACCEPTABLE.equals(status)
                || HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.equals(status)) {
            return new EMBadRequestException(reason);
        } else if (HttpResponseStatus.UNAUTHORIZED.equals(status)) {
            return new EMUnauthorizedException(reason);
        } else if (HttpResponseStatus.PAYMENT_REQUIRED.equals(status)
                || HttpResponseStatus.FORBIDDEN.equals(status)) {
            return new EMForbiddenException(reason);
        } else if (HttpResponseStatus.NOT_FOUND.equals(status)) {
            return new EMNotFoundException(reason);
        } else if (HttpResponseStatus.TOO_MANY_REQUESTS.equals(status)) {
            return new EMTooManyRequestsException(reason);
        } else if (HttpResponseStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            return new EMInternalServerErrorException(reason);
        } else if (HttpResponseStatus.BAD_GATEWAY.equals(status)) {
            return new EMBadGatewayException(reason);
        } else if (HttpResponseStatus.SERVICE_UNAVAILABLE.equals(status)) {
            return new EMServiceUnavailableException(reason);
        } else if (HttpResponseStatus.GATEWAY_TIMEOUT.equals(status)) {
            return new EMGatewayTimeoutException(reason);
        }
        return new EMUnknownException(reason);
    }

}
