package com.mbition.hybridcloud.reminders.controller;


import com.mbition.hybridcloud.reminders.exceptions.NotAuthorizedException;
import com.mbition.hybridcloud.reminders.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public String handleNotAuthorized() {
        log.warn("Not authorized");
        return "Not authorized";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception e) {
        log.error("Unhandled Exception occurred", e);
        return "Internal error";
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundError(NotFoundException ex) {
        log.error("Reminders not found");
        return "No reminders exists";
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.error("Method not allowed", e);
        return "Method not allowed";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public String handleBadRequest(HttpClientErrorException.BadRequest ex) {
        log.warn("Bad request");
        return "Bad request because :" + ex.getMessage();
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentError(IllegalArgumentException ex) {
        log.error("Illegal argument");
        return "Illegal argument: " + ex.getMessage();
    }
}
