package com.diabeaten.edgeservice.handler;

import com.diabeaten.edgeservice.exception.AccessNotAllowedException;
import com.diabeaten.edgeservice.exception.UserClientNotWorkingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserClientNotWorkingException.class)
    public void userClientNotWorkingExceptionHandler(UserClientNotWorkingException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(AccessNotAllowedException.class)
    public void accessNotAllowedExceptionHandler(AccessNotAllowedException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
    }
}
