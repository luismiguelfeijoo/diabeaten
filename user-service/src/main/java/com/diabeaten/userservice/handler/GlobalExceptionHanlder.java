package com.diabeaten.userservice.handler;

import com.diabeaten.userservice.exceptions.DuplicatedUsernameException;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHanlder {
    @ExceptionHandler(NoSuchUserException.class)
    public void noSuchUserExceptionHandler(NoSuchUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public void noSuchUserExceptionHandler(DuplicatedUsernameException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_CONFLICT, exception.getMessage());
    }
}
