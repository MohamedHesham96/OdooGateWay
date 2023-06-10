package com.bluesoft.OdooGateWay.Global.ExceptionHandler;

import com.bluesoft.OdooGateWay.Global.exceptions.CommandException;
import com.bluesoft.OdooGateWay.Global.reposnes.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CommandException.class})
    protected ResponseEntity<Response> handleCommandException(CommandException exception) {
        Response response = new Response(exception.getMessage(), exception.getOdooMessage(), exception.getHttpStatus());
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Response> handleInvalidArguments(MethodArgumentNotValidException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        Response response = new Response(errorMap.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
