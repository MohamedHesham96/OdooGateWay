package com.bluesoft.OdooGateWay.Global.ExceptionHandler;

import com.bluesoft.OdooGateWay.Global.exceptions.CommandException;
import com.bluesoft.OdooGateWay.Global.reposnes.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CommandException.class})
    protected ResponseEntity<Response> handleCommandException(CommandException exception) {
        Response response = new Response(exception.getMessage(), exception.getOdooMessage(), exception.getHttpStatus());
        return new ResponseEntity(response, exception.getHttpStatus());
    }
}
