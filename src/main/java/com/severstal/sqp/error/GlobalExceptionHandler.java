package com.severstal.sqp.error;

/**
 * Global exception handler.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.error
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.tomcat.jdbc.pool.ConnectionPool.getStackTrace;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(Throwable.class)
    public ModelAndView genericExceptionHandler(Exception ex) {

        String errorMessage = ex.getMessage();
        String errorStacktrace = getStackTrace(ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("errorMessage", errorMessage);
        mv.addObject("stackTrace", errorStacktrace);
        return mv;
    }
}
