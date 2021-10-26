package com.easemob.im.server.api;

import com.easemob.im.server.EMErrorResponse;
import com.easemob.im.server.EMException;
import com.easemob.im.server.exception.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.netty.http.client.HttpClientResponse;

import java.io.IOException;

public class DefaultErrorMapper implements ErrorMapper {

    private ObjectMapper objectMapper;

    private HttpClientResponse httpClientResponse;

    private int statusCode;

    public DefaultErrorMapper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void statusCode(HttpClientResponse response) {
        this.statusCode = response.status().code();
        this.httpClientResponse = response;
    }

    @Override
    public void checkError(ByteBuf buf) {
        if (this.statusCode >= 400) {
            throw toException(this.httpClientResponse, decode(buf, EMErrorResponse.class));
        }
    }

    private EMException toException(HttpClientResponse response, EMErrorResponse errorResponse) {
        String reason =
                String.format("%s %s -> %d %s, \n error_description -> %s", response.method().toString(), response.uri(),
                        response.status().code(), response.status().reasonPhrase(), errorResponse.getErrorDescription());
        HttpResponseStatus status = response.status();
        EMException emException = new EMUnknownException(reason);
        if (HttpResponseStatus.BAD_REQUEST.equals(status)
                || HttpResponseStatus.METHOD_NOT_ALLOWED.equals(status)
                || HttpResponseStatus.NOT_ACCEPTABLE.equals(status)
                || HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.equals(status)) {
            emException = new EMBadRequestException(reason);
        } else if (HttpResponseStatus.UNAUTHORIZED.equals(status)) {
            emException = new EMUnauthorizedException(reason);
        } else if (HttpResponseStatus.PAYMENT_REQUIRED.equals(status)
                || HttpResponseStatus.FORBIDDEN.equals(status)) {
            emException = new EMForbiddenException(reason);
        } else if (HttpResponseStatus.NOT_FOUND.equals(status)) {
            emException = new EMNotFoundException(reason);
        } else if (HttpResponseStatus.TOO_MANY_REQUESTS.equals(status)) {
            emException = new EMTooManyRequestsException(reason);
        } else if (HttpResponseStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            emException = new EMInternalServerErrorException(reason);
        } else if (HttpResponseStatus.BAD_GATEWAY.equals(status)) {
            emException = new EMBadGatewayException(reason);
        } else if (HttpResponseStatus.SERVICE_UNAVAILABLE.equals(status)) {
            emException = new EMServiceUnavailableException(reason);
        } else if (HttpResponseStatus.GATEWAY_TIMEOUT.equals(status)) {
            emException = new EMGatewayTimeoutException(reason);
        }
        //设置错误状态码
        emException.setErrorCode(statusCode);
        return emException;
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
            throw new EMJsonException(
                    String.format("could not decode class %s: %s", tClass.getName(),
                            e.getMessage()), e);
        }
    }

}