package com.vicheak.coreapi.exception;

import com.vicheak.coreapi.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleServiceException(ResponseStatusException ex){
        return BaseError.builder()
                .isSuccess(false)
                .message("Server has been errored, please check!")
                .code(ex.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .errors(ex.getReason())
                .build();
    }

}
