package com.bluesoft.OdooGateWay.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommandException extends Exception {

    private String message;
    private String odooMessage;
    private HttpStatus httpStatus;

    public CommandException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CommandException(String message, String odooMessage, HttpStatus httpStatus) {
        this.message = message;
        this.odooMessage = odooMessage;
        this.httpStatus = httpStatus;
    }
}
