package no.linska.webapp.controller;

import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;


@ControllerAdvice
public class ExceptionHandlingController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TransientPropertyValueException.class)
    public String handleTransientPropertyValueException(HttpServletRequest req, Exception ex) {
        System.out.println("INTERNAL SERVER ERROR 1");
        LOGGER.log(Level.SEVERE,req.toString());
        LOGGER.log(Level.SEVERE,ex.toString());
        return "/error";
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, Exception ex) {
        System.out.println("INTERNAL SERVER ERROR 2");
        LOGGER.log(Level.SEVERE,req.toString());
        LOGGER.log(Level.SEVERE,ex.toString());

        return "/error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(HttpServletRequest req, Exception ex) {
        LOGGER.log(Level.SEVERE,req.toString());
        LOGGER.log(Level.SEVERE,ex.toString());
        return "/error";
    }


}
