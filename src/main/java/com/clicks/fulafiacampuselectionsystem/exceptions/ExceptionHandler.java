package com.clicks.fulafiacampuselectionsystem.exceptions;

import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse> handleNotFound(RuntimeException exception) {
        return getExceptionMessage(HttpStatus.NOT_FOUND, exception);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
            {EntityExistException.class,
            InvalidParamsException.class})
    public ResponseEntity<CustomResponse> handleBadRequest(RuntimeException exception) {
        return getExceptionMessage(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<CustomResponse> getExceptionMessage(HttpStatus notFound, RuntimeException exception) {
        return ResponseEntity.status(notFound)
                .body(new CustomResponse(false, exception.getMessage()));
    }

}
