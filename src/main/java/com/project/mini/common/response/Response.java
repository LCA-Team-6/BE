package com.project.mini.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Response<T> {
    private int status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public static <T> Response<T> success(String message, T data) {
        return Response.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Response<T> fail(HttpStatus status, String message) {
        return Response.<T>builder()
                .status(status.value())
                .message(message)
                .error(status.getReasonPhrase())
                .build();
    }
}
