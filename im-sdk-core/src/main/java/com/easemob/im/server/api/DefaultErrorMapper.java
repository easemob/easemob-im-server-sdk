package com.easemob.im.server.api;

import com.easemob.im.server.EMException;
import com.easemob.im.server.exception.EMBadGatewayException;
import com.easemob.im.server.exception.EMBadRequestException;
import com.easemob.im.server.exception.EMForbiddenException;
import com.easemob.im.server.exception.EMGatewayTimeoutException;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import com.easemob.im.server.exception.EMMethodAllowedException;
import com.easemob.im.server.exception.EMNotAcceptableException;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.exception.EMServiceUnavailableException;
import com.easemob.im.server.exception.EMTooManyRequestsException;
import com.easemob.im.server.exception.EMUnauthorizedException;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultErrorMapper implements ErrorMapper {

    private static final Logger log = LogManager.getLogger();

    private Map<HttpResponseStatus, Class<? extends EMException>> mappers;

    public DefaultErrorMapper() {
        this.mappers = new ConcurrentHashMap<>();
        register(HttpResponseStatus.BAD_REQUEST, EMBadRequestException.class);
        register(HttpResponseStatus.UNAUTHORIZED, EMUnauthorizedException.class);
        register(HttpResponseStatus.FORBIDDEN, EMForbiddenException.class);
        register(HttpResponseStatus.NOT_FOUND, EMNotFoundException.class);
        register(HttpResponseStatus.METHOD_NOT_ALLOWED, EMMethodAllowedException.class);
        register(HttpResponseStatus.NOT_ACCEPTABLE, EMNotAcceptableException.class);
        register(HttpResponseStatus.TOO_MANY_REQUESTS, EMTooManyRequestsException.class);
        register(HttpResponseStatus.INTERNAL_SERVER_ERROR, EMInternalServerErrorException.class);
        register(HttpResponseStatus.BAD_GATEWAY, EMBadGatewayException.class);
        register(HttpResponseStatus.SERVICE_UNAVAILABLE, EMServiceUnavailableException.class);
        register(HttpResponseStatus.GATEWAY_TIMEOUT, EMGatewayTimeoutException.class);

    }

    public void register(HttpResponseStatus status, Class<? extends EMException> exception) {
        this.mappers.put(status, exception);
        log.debug("http error mapper registered for status code {}, map to exception {}", status.code(), exception);
    }

    public void unregister(HttpResponseStatus status) {
        this.mappers.remove(status);
        log.debug("http error mapper unregistered for status code {}", status.code());
    }

    @SuppressWarnings("unchecked")
    public Mono<HttpClientResponse> apply(HttpClientResponse response) {
        int code = response.status().code();
        Class<? extends EMException> errorClass = this.mappers.get(response.status());

        if (errorClass == null) {
            return Mono.just(response);
        }

        Constructor<?>[] ctors = errorClass.getConstructors();
        for (int i = 0; i < ctors.length; i++) {
            if (ctors[i].getParameterCount() == 1 && ctors[i].getParameterTypes()[0] == String.class) {
                EMException error;
                try {
                    error = (EMException) ctors[i].newInstance(response.status().toString());
                } catch (InstantiationException e) {
                    return Mono.error(new EMUnknownException(e.getMessage()));
                } catch (IllegalAccessException e) {
                    return Mono.error(new EMUnknownException(e.getMessage()));
                } catch (InvocationTargetException e) {
                    return Mono.error(new EMUnknownException(e.getMessage()));
                }
                return Mono.error(error);
            }
        }
        return Mono.error(new EMUnknownException(response.status().toString()));


    }

}
