package sap.practice.mobilesystem.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ch.qos.logback.classic.Logger;

@ControllerAdvice
public class ErrorController {
	private static org.jboss.logging.Logger logger = LoggerFactory.logger(ErrorController.class);
/*
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
    */
}
