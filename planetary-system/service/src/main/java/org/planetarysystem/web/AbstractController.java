package org.planetarysystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRequestError(HttpServletRequest request, Exception e) {
        String error = String.format("Request to %s failed", request.getRequestURI());
        logger.error(error, e);
        return error;
    }
}
