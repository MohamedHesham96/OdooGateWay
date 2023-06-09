package com.bluesoft.OdooGateWay.requestAndReposne;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class Response {
    private Object data;
    private String time;
    private String errorMessage;
    private String odooErrorMessage;
    private HttpStatus status;

    public Response(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public Response(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public Response(String errorMessage, String odooErrorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.odooErrorMessage = odooErrorMessage;
        this.status = status;
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
