package com.diabeaten.informationservice.handler;

import com.diabeaten.informationservice.exceptions.InformationNotFoundException;
import com.diabeaten.informationservice.exceptions.InvalidHourFormatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidHourFormatException.class)
    public void invalidHourFormatExceptionHandler(InvalidHourFormatException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InformationNotFoundException.class)
    public void informationNotFoundExceptionHandler(InformationNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
    }
}
